#spring-boot-demo
#spring-boot  + log4j
1.关于JSch进入oracle服务器后，找不到dbca命令的原因是，对方的环境变量：
export ORACLE_BASE=/oracle/app/oracle
export ORACLE_SID=ORCLRAC1
export ORACLE_UNQNAME=ORCLRAC
export ORACLE_HOME=/oracle/app/oracle/product/11.2/db_1
export NLS_LANG=AMERICAN_AMERICA.ZHS16GBK
export PATH=$ORACLE_HOME/bin:$ORACLE_HOME/OPatch:$PATH
写在了./bash_profile里面了，需要将以上变量复制一份，写入/etc/profile里面，然后source /etc/profile

2.jdbc 连接oracle 数据库集群（RAC）和单节点