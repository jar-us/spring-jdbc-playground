package jar.us.springjdbcplayground

import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.stream.Stream

@Table("product") // Important: Specify the table name
data class ProductEntity(@Id val id: Long?, val name: String, val price: Double)

@Repository
interface ProductSpringDataJdbcRepository : CrudRepository<Product, Long> {

    @Query("SELECT * FROM product")
    fun findAllAsStream(): Stream<ProductEntity>

    // Define a query that maps to a DTO projection
    @Query("SELECT p.name, p.price FROM product p")
    fun findNameAndPrice(): List<ProductNameAndPrice>

}


data class ProductNameAndPrice(
    val name: String,
    val price: Double,
)

interface ProductProjection {
    val name: String
    val price: Double
}

class ProductProjectionImpl(
    override val name: String,
    override val price: Double
) : ProductProjection