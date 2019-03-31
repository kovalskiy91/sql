package com.kovalskiy91.selfdev.sql.postgres

class DQLIntSpec extends PostgresDatabaseIntSpec {

    def setup() {
        //employee
        sql.execute("create table employee (" +
                "id serial," +
                "fname text, " +
                "lname text, " +
                "constraint pk_employee primary key (id)" +
                ")")
        sql.execute("insert into employee (fname, lname) values ('Michael', 'Smith')")
        sql.execute("insert into employee (fname, lname) values ('Susan',  'Barker')")
        sql.execute("insert into employee (fname, lname) values ('Robert',  'Tyler')")
        sql.execute("insert into employee (fname, lname) values ('Susan',  'Hawthorne')")
        sql.execute("insert into employee (fname, lname) values ('John',  'Gooding')")
        sql.execute("insert into employee (fname, lname) values ('Helen',  'Fleming')")
        sql.execute("insert into employee (fname, lname) values ('Chris',  'Tucker')")
        sql.execute("insert into employee (fname, lname) values ('Sarah',  'Parker')")
        sql.execute("insert into employee (fname, lname) values ('Jane',  'Grossman')")
        sql.execute("insert into employee (fname, lname) values ('Paula', 'Roberts')")
        sql.execute("insert into employee (fname, lname) values ('Thomas', 'Ziegler')")
        sql.execute("insert into employee (fname, lname) values ('Samantha', 'Jameson')")
        sql.execute("insert into employee (fname, lname) values ('John', 'Blake')")
        sql.execute("insert into employee (fname, lname) values ('Cindy', 'Mason')")
        sql.execute("insert into employee (fname, lname) values ('Frank', 'Portman')")
        sql.execute("insert into employee (fname, lname) values ('Theresa', 'Markham')")
        sql.execute("insert into employee (fname, lname) values ('Beth', 'Fowler')")
        sql.execute("insert into employee (fname, lname) values ('Rick', 'Tulman')")

        //department
        sql.execute("create table department(" +
                "id serial," +
                "name text," +
                "constraint pk_department primary key (id)," +
                "constraint uq_name unique (name), " +
                "constraint nn_name check (name is not null)" +
                ")")
        sql.execute("insert into department (name) values ('Operations')")
        sql.execute("insert into department (name) values ('Loans')")
        sql.execute("insert into department (name) values ('Administration')")
    }

    def "should select database version"() {
        expect:
        def version = sql.firstRow("select version()").version as String
        version.startsWith("PostgreSQL 11.1")
    }

    def "should select all employees"() {
        expect:
        sql.rows("select * from employee").size() == 18
    }

    def "should select names of all departments"() {
        when:
        def rows = sql.rows("select name from department")

        then:
        rows.collect({ it.name }).toSet() == ['Operations', 'Loans', 'Administration'].toSet()
    }

    def "should select expressions from employee"() {
        when:
        def rows = sql.rows("select 'ACTIVE' as state, id * 10 as idx10, id, upper(fname) as upper_fname, fname from employee")

        then:
        rows.each {
            println(it)
            assert it.getAt("state") == 'ACTIVE'
            assert it.getAt("idx10") == it.getAt("id") * 10
            assert it.getAt("upper_fname") == it.getAt("fname").toString().toUpperCase()
        }
    }

    def "should select from temporal query"() {
        expect:
        def rows = sql.rows("select te.employee_id, te.fname from (" +
                "select e.id as employee_id, e.fname from employee e" +
                ") te")
        rows.each { println(it) }
        rows.size() == 18
    }

    def "should select number of employees having same name"() {
        expect:
        def rows = sql.rows("select count(e.id), e.fname from employee e group by e.fname")
        println(rows)
        rows.size() == 16
    }

    def "should select when there are more than 2 employees having same name"() {
        expect:
        def rows = sql.rows("select count(e.id), e.fname from employee e group by e.fname having count(e.id) > 1")
        println(rows)
        rows.each {
            assert it.count > 1
        }
    }

}
