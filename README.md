# base


## test

### git test

*   init
*   push
*   pull
*   status
*   add
*   remote
*   clone
 
### gradle test

*   init

### java test
 
* lambda test

* file read test 

* utf-8 bom test

* array toString test

### sql test

#### mysql

* tableName,columnName default case-insensitive (MACOS hfs+). mysql 表名大小写是否敏感 默认设置与操作系统和文件系统相关。

* collate; _ci/_cs/_bin: case-insensitive/case-sensitive/binary;  utf8_geneal_ci, utf8_bin；collate 影响值的比较和记录的排序。在 collate = *_ci 时，将内容 to_lowercase 后进行比较，会出现 'a' == 'A' 的情况，可以利用 （binary）  'a' ！=  binary 'A'

* 可以通过 在 my.conf 文件 mysqld 下面添加配置项 lower_case_table_names = 1 来让表名大小写不敏感;


## code backup

### java

#### util

* file copy
 

* excel read/write


* http httpClient

    - http
    - https
    - keyStore


* codec

    - AES
    - RSA
    - MessageDigest


### css

* template.css

### js

#### backbone 

* elementView

#### util

* stringUtil 


## document backup

### browser

*   bookmarks
