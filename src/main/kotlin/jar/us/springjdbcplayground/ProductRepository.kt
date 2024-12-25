package jar.us.springjdbcplayground

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowCallbackHandler
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.io.FileWriter
import java.util.stream.Collectors

data class Product(val id: Long, val name: String, val price: Double)

@Repository
class ProductRepository(
    private val jdbcTemplate: JdbcTemplate,
    private val productSpringDataJdbcRepository: ProductSpringDataJdbcRepository,
) {

    fun getAllProductsWithRowMapper(): List<Product> {
        val products = jdbcTemplate.query("SELECT id, name, price FROM product", RowMapper { rs, _ ->
            Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getDouble("price")
            )
        })
        return products // All products are now in this list
    }

    fun processProductsWithRowCallbackHandler() {
        val writer = FileWriter("products.txt") // Example: writing to a file

        jdbcTemplate.query("SELECT id, name, price FROM product", RowCallbackHandler { rs ->
            val id = rs.getLong("id")
            val name = rs.getString("name")
            val price = rs.getDouble("price")

            // Process the data immediately (e.g., write to file, send to another system)
            writer.write("ID: $id, Name: $name, Price: $price\n")
        })

        writer.close()
    }

    fun processProductsWithFetchSize(fetchSize: Int) {
        jdbcTemplate.fetchSize = fetchSize // Set the fetch size

        jdbcTemplate.query("SELECT id, name, price FROM product", RowCallbackHandler { rs ->
            val id = rs.getLong("id")
            val name = rs.getString("name")
            val price = rs.getDouble("price")
            // Process the data (e.g., log, write to file)
            println("Fetched with size $fetchSize - ID: $id, Name: $name, Price: $price")
        })
    }

    fun processProductsWithStreaming(): List<ProductEntity> {
        return productSpringDataJdbcRepository.findAllAsStream().use { stream -> // Important: use() to close the stream
            stream.collect(Collectors.toList())//or other operation such as foreach
        }
    }

    fun getProductsByPage(pageNumber: Int, pageSize: Int): List<Map<String, Any>> {
        val offset = pageNumber * pageSize
        val sql = "SELECT id, name, price FROM product LIMIT ? OFFSET ?"
        return jdbcTemplate.queryForList(sql, pageSize, offset)
    }
}