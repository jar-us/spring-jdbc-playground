package jar.us.springjdbcplayground

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class MyRunner(private val productRepository: ProductRepository) : CommandLineRunner {

    override fun run(vararg args: String?) {
        println("Using RowMapper:")
        val products = productRepository.getAllProductsWithRowMapper()
        products.forEach { println(it) }

        println("\nUsing RowCallbackHandler:")
        productRepository.processProductsWithRowCallbackHandler()
        println("Products written to products.txt")

        println("\nUsing Fetch Size:")
        productRepository.processProductsWithFetchSize(5)

        println("\nUsing Streaming:")
        productRepository.processProductsWithStreaming().forEach { println(it) }

        println("\nUsing Pagination:")
        val page1 = productRepository.getProductsByPage(0, 3)
        println("Page 1: $page1")
        val page2 = productRepository.getProductsByPage(1, 3)
        println("Page 2: $page2")
    }
}