package jar.us.springjdbcplayground

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringJdbcPlaygroundApplication

fun main(args: Array<String>) {
    runApplication<SpringJdbcPlaygroundApplication>(*args)
}
