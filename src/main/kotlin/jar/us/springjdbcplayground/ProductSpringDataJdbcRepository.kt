package jar.us.springjdbcplayground

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.stream.Stream

@Table("product") // Important: Specify the table name
data class ProductEntity(@Id val id: Long?, val name: String, val price: Double)

@Repository
interface ProductSpringDataJdbcRepository : CrudRepository<Product, Long> {

    @Query("SELECT * FROM product")
    fun findAllAsStream(): Stream<ProductEntity>
}