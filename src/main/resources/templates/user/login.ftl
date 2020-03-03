<html>
<head>
    <title>卖家管理系统</title>
    <link href="/sell/css/stylelogin.css" rel='stylesheet' type='text/css' />
</head>
<body>
<h1>登录卖家管理系统</h1>
<div class="login-form">
    <div class="close"> </div>
    <div class="head-info">
        <label class="lbl-1"> </label>
        <label class="lbl-2"> </label>
        <label class="lbl-3"> </label>
    </div>
    <div class="clear"> </div>

    <form action="/sell/seller/login" method="post">
        <input type="text" name="name" class="text" value="Username" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Username';}" >
        <div class="key">
            <input type="password" name="password" value="Password" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Password';}">
        </div>
    <div class="signin">
        <input type="submit" value="Login" >
    </div>
    </form>
</div>

</body>
</html>