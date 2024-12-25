package jar.us.springjdbcplayground

import jakarta.annotation.PostConstruct
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class DatabaseInitializer(private val jdbcTemplate: JdbcTemplate) {

    @PostConstruct // This method will be called after the bean is constructed
    fun initializeDatabase() {
        // Create the table (if it doesn't exist)
        jdbcTemplate.execute(
            """
            CREATE TABLE IF NOT EXISTS product (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                description TEXT,
                price DECIMAL(10, 2) NOT NULL,
                category VARCHAR(50)
            )
        """.trimIndent()
        )

        // Check if data already exists (to avoid duplicates on restart)
        val count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM product", Int::class.java)

        if (count == 0) {
            // Insert sample data
            val inserts = listOf(
                "('Laptop Pro X', 'High-performance laptop with 16GB RAM', 1299.99, 'Electronics')",
                "('Wireless Mouse', 'Ergonomic wireless mouse', 29.99, 'Electronics')",
                "('Office Chair', 'Comfortable office chair with adjustable height', 199.00, 'Furniture')",
                "('Wooden Desk', 'Solid wood desk with drawers', 299.50, 'Furniture')",
                "('Java Programming Book', 'A comprehensive guide to Java programming', 49.99, 'Books')",
                "('Python Cookbook', 'Recipes for mastering Python', 39.99, 'Books')",
                "('Running Shoes', 'Lightweight running shoes for optimal performance', 89.95, 'Apparel')",
                "('Cotton T-Shirt', 'Soft and comfortable cotton t-shirt', 19.99, 'Apparel')"
            )
            inserts.forEach {
                jdbcTemplate.update("INSERT INTO product (name, description, price, category) VALUES $it")
            }
            println("Product table created and sample data inserted.")
        } else {
            println("Product table already exists with data.")
        }
    }
}