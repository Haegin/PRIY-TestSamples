package in.haeg.stablemarriage;

import java.util.HashMap;

public class Student extends Thread {
	private HashMap<Integer, Project> ranking = new HashMap<Integer, Project>();
	private Boolean rejected = Boolean.TRUE;
	private Boolean run = Boolean.TRUE;
	private int rejections = 0;
	private Project currProject;
	private Coordinator coord;
	private String id;

	public Student(Coordinator c, int id) {
		coord = c;
		this.id = "Student_" + id;
		//System.out.println("Student " + this.getID() + " created.");
	}

	public void setRankings(HashMap<Integer, Project> a_Ranking) {
		ranking = a_Ranking;
	}

	public void run() {
		while (run) {
			/* If we are currently in a state of rejection then lets propose to a project */
			if (rejected) {
				currProject = ranking.get(rejections + 1);
				//System.out.println("Student " + this.getID() + " proposing to Project " + currProject.getID());
				rejected = !(currProject.propose(this));
				if (rejected) {
					rejections += 1;
				} else {
					coord.registerMarriage();
					System.out.println("Sleeping" + id);
				}
			/* Otherwise we'll have a nap... */
			} else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					rejected = Boolean.TRUE;
				}
			}
		}
		//System.out.println(this.id + " done. Exiting.");
	}

	public void printResults() {
		//System.out.println(id + " married to " + currProject.getID());
	}

	public void spurn() {
		coord.registerDivorce();
		System.out.println("Waking" + id);
	}

	public void terminate() {
		run = Boolean.FALSE;
		if (!rejected) {
			this.interrupt();	// Interrupt this if we are sleeping, otherwise it'll get dull...
		}
	}

	public String getID() {
		return id;
	}

}
