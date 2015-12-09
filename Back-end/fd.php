<?php
class Test {
	private $id;
    private $title;
    private $mark;
    private $maxMark;
    private $startDate;
    private $endDate;
    private $time;
    private $done = false;
	
	public function __construct($id, $t, $m, $mm, $sd, $ed, $ti){
		$this->id = $id;
		$this->title = $t;
		$this->mark = $m;
		$this->maxMark = $mm;
		$this->startDate = $sd;
		$this->endDate = $ed;
		$this->time = $ti;
		if ($m!=null)
			$this->done = true;		
	}

	public function getId(){
		return $this->id;
	}

	public function isNear(){
		return true;
	}

	public function getTitle(){
		return $this->title;
	}

	public function getStartDate(){
		return $this->startDate;
	}

	public function getFullInfo(){
		return array(utf8_encode($this->title), $this->mark, $this->maxMark, $this->startDate, $this->endDate, $this->time, $this->done);
	}

}

class Subject {
	private $id;
	private $title;
	private $tests;

	public function __construct($id, $t, $te){
		$this->id = $id;
		$this->title = $t;
		$this->tests = $te;
	}

	public function getTitle(){
		return $this->title;
	}

	public function getId(){
		return $this->id;
	}

	public function getNextTests($max){
		$ret = array();		
		foreach ($this->tests as $t){
			if ($max>0){	// Max of tests to show
				if ($t->isNear()) 
					$ret[] = array(utf8_encode($t->getTitle()), $t->getStartDate());
				$max--;						
			} else
				break;					
		}
		return $ret;
	}

	public function getArray(){
		$ret = array();
		$ret[] = $this->id;
		$ret[] = utf8_encode($this->title);
		$retTests = array();
		foreach ($this->tests as $t) {
			$retTests[] = $t->getFullInfo();
		}
		$ret[] = $retTests;
		return $ret;
	}
}

class User {
	private $id;
	public $user;
	private $password;
	private $email;
	private $subjects;

	public function __construct($id, $u, $p, $e, $s){
		$this->id = $id;
		$this->user = $u;
		$this->password = $p;
		$this->email = $e;
		$this->subjects = $s;
	}

	public function getUsername(){
		return $this->user;
	}

	public function getId(){
		return $this->id;
	}

	public function getOverview(){
		$ret = array();
		foreach ($this->subjects as $s){
			$ret[] = array($s->getId(), utf8_encode($s->getTitle()), $s->getNextTests(3));
		}
		return $ret;
	}

	public function getSubjectPanel($subjectID){
		return $this->subjects[$subjectID]->getArray();
	}
}

class FakeData{
	private $users;

	public function __construct(){
		
		/* Subject 1 */
		$s1 = new Subject(0, "Matemáticas avanzadas", array(
				new Test(0, "Parcial 1", 4, 10, "10/11/2015 11:00:00", "10/11/2015 11:09:00", "00:09:00"),
				new Test(1, "Parcial 2", 5.3, 7, "15/11/2015 11:00:00", "15/11/2015 11:15:00", "00:15:00"),
				new Test(2, "Final", null, 10, "21/11/2015 08:30:00", "21/11/2015 08:55:00", "00:25:00")
			));

		/* Subject 2 */
		$s2 = new Subject(1, "Lógica", array(
				new Test(0, "Parcial 1", 6.35, 10, "20/11/2015 09:00:00", "20/11/2015 10:00:00", "01:00:00"),
				new Test(1, "Parcial 2", 7.8, 10, "25/11/2015 12:00:00", "25/11/2015 12:25:00", "00:25:00"),
				new Test(2, "Parcial 3", null, 10, "03/12/2015 14:00:00", "03/12/2015 15:30:00", "01:30:00"),
				new Test(3, "Final 3", null, 10, "12/01/2016 18:00:00", "12/01/2016 19:10:00", "01:10:00")
			));

		/* Subject 3 */
		$s3 = new Subject(2, "Cálculo", array(
				new Test(0, "Cálculo avanzado", 6.35, 10, "20/11/2015 09:00:00", "20/11/2015 10:00:00", "01:00:00"),
				new Test(1, "Cálculo aplicado", 7.8, 10, "25/11/2015 12:00:00", "25/11/2015 12:25:00", "00:25:00"),
				new Test(2, "Final Matemáticas", null, 10, "03/12/2015 14:00:00", "03/12/2015 15:30:00", "01:30:00")
			));
		$subjects = array($s1, $s2, $s3);
		$u = new User(0, "Manolo", "123", "manolo@gmail.com", $subjects);		
		$this->users = array($u);
	}

	public function getOverview($id_u){
		$id_u = $this->getUserId($id_u);
		if ($id_u>=0)		
			return $this->users[$id_u]->getOverview();		
		else 
			return null;
	}

	public function getSubjectPanel($id_u, $subjectID){
		$id_u = $this->getUserId($id_u);
		if ($id_u>=0)		
			return $this->users[$id_u]->getSubjectPanel($subjectID);		
		else 
			return null;
	}

	private function getUserId($id_u){
		foreach ($this->users as $u){
			if ($u->getUsername()==$id_u)
				return $u->getId();
		}
		return -1;		
	}


}?>