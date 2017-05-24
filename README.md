# WebProject_VehicleTest
<h2>汽车检测线远程故障诊断系统</h2>

<h4>version:1.0.0</h4>
用SpringBoot SpringMVC MyBatis框架写的一个简单的Web项目，与实际的一个检测线故障诊断项目结合。用户上传诊断数据，由服务器返回诊断结果（诊断结果功能还未加入）。
<br>
目前有用户的注册、登录、登出，上传文件，解析文件功能。后续会继续完善。
<br><br>
文件结构：
<table>
  <tr>
    <td>controller:</td>
    <td>HomeController，LoginController,RegisterController</td>
  </tr>
  <tr>
    <td>service:</td>
    <td>UserService,UpFileService</td>
  </tr>
  <tr>
    <td>DAO:</td>
    <td>UserDAO,UpFileDAO</td>
  </tr>
  <tr>
    <td>model:</td>
    <td>User,UpFile</td>
  </tr>
  <tr>
    <td>工具类:</td>
    <td>VehicleTestUtil</td>
  </tr>
</table>
