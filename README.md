# DBCP-Test
a test of DBCP in （c3p0、dbcp、druid、tomcat-jdbc、hikaricp），with generating report

# Test Results
![result](http://ww1.sinaimg.cn/large/005T6w1dgy1fruvjcaav9j30m80godfv.jpg)

# Note
**not support java9** , (sigar not support)

please put Profile-specific Properties on running Directory ,which report will generating in.

#command
+ config : generate two Profile-specific Properties on running Directory
> eg: java -jar dbcp-test.jar config
+ : run script
> eg: java -jar dbcp-test.jar