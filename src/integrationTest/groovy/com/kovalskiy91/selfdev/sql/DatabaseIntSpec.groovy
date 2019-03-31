package com.kovalskiy91.selfdev.sql

import com.zaxxer.hikari.HikariDataSource
import spock.lang.Specification

class DatabaseIntSpec extends Specification {

    def dataSourceFrom(Class driver, String jdbcUrl, String username, String password) {
        def dataSource = new HikariDataSource()
        dataSource.driverClassName = driver.name
        dataSource.jdbcUrl = jdbcUrl
        dataSource.username = username
        dataSource.password = password
        dataSource
    }

}
