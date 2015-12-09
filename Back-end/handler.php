<?php	
	include_once('connection.php');
	include_once('fd.php');
	$db = new FakeData();
	if (isset($_POST['func']))
		switch($_POST['func']){	/* Check where you come from and what you ask for */
			case 'login': /* Return username if login suceed */ 			
				$username = checkLogin($dbCon, $_POST['username'], $_POST['password']);
				$ret = array();
				$ret['username'] = $username;
				echo json_encode($ret);
				mysqli_close($dbCon);
			break;
			case 'getOverview': /* Return subjects and next tests of each subject */ 
				$username = checkLogin($dbCon, $_POST['username'], $_POST['password']);
				$overview = getOverview($db, $username);
				if ($overview!=null)
					echo json_encode($overview);				
				 else 				
					echo json_encode(null);					
				mysqli_close($dbCon);
			break;
			case 'getSubjectPanel':	/* Return info of given subject */
				$username = checkLogin($dbCon, $_POST['username'], $_POST['password']);
				$subjectPanel = getSubjectPanel($db, $username, $_POST['subjectID']);
				if($subjectPanel!=null)
					echo json_encode($subjectPanel);
				else
					echo json_encode(null);
				mysqli_close($dbCon);
			break;
			default: /* Be quiet, someone got here while being not allowed */
				header("Location: http://www.hostinger.es/error_404");	/* Lets be ghosts */
			break;
		}
	else header("Location: http://www.hostinger.es/error_404");	/* Lets be ghosts */

	function checkLogin($dbCon, $username, $password){
		return mysqli_fetch_row(mysqli_query($dbCon, "SELECT username FROM users WHERE username = '$username' AND password = '$password'"))[0];
	}

	function getOverview($db, $username){		
		return $db->getOverview($username);
	}

	function getSubjectPanel($db, $username, $subjectID){
		return $db->getSubjectPanel($username, $subjectID);
	}
?>