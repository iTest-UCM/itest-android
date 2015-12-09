<?php
	define('HOST', '');
	define('USERNAME', '');
	define('PASSWORD', '');
	define('DB_NAME', '');
	$dbCon = mysqli_connect(HOST, USERNAME, PASSWORD, DB_NAME)
				or die ('Unable to connect to the DB');
?>