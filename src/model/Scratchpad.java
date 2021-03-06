package model;
/**
 * Created by Ashwin Ignatius on 11/4/2017.
 */

import javafx.scene.control.Alert;

import java.io.*;
import java.util.*;

public class Scratchpad implements Serializable {
    private HashMap<String, Course> courses;

    private HashMap<String, Program> programs;

    private HashMap<String, Instructor> instructors;

    private HashMap<String, Student> students;

    private HashMap<String, String> coursesTaught;

    private List<Request> requests;

    private int cycle;

    private int term;

    private Integer year;

    private String[] terms;

    private HashMap<String, Integer> coursesRequested;

    private List<String> courseSuggestions;

    private String testCaseDirectory;

    private boolean restartedSim;

    public Scratchpad() {
        this.courses = new HashMap<>();
        this.programs = new HashMap<>();
        this.instructors = new HashMap<>();
        this.students = new HashMap<>();
        this.coursesTaught = new HashMap<>();
        this.cycle = 0;
        this.term = 3;
        this.year = 2017;
        this.terms = new String[]{"Winter", "Spring", "Summer", "Fall"};
        this.requests = new ArrayList<>();
        this.coursesRequested = new HashMap<>();
        this.courseSuggestions = new ArrayList<>();
        this.testCaseDirectory = "";
        this.restartedSim = false;
    }

    private void processFileContents(String inputFileName, String[] tokens) {
        int cut = inputFileName.lastIndexOf("/");
        String fileToParse = inputFileName.substring(cut + 1);

        switch (fileToParse) {
            case "courses.csv":
                courses.put(tokens[0], new Course(tokens[0], tokens[1], tokens[2]));
                break;
            case "instructors.csv":
                instructors.put(tokens[0], new Instructor(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4]));
                break;
            case "listings.csv":
                programs.get(tokens[0]).addListing(tokens[1]);
                break;
            case "prereqs.csv":
                courses.get(tokens[0]).addPrereq(tokens[1]);
                break;
            case "programs.csv":
                programs.put(tokens[0], new Program(tokens[0], tokens[1]));
                break;
            case "students.csv":
                students.put(tokens[0], new Student(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4]));                break;
            default:
                System.out.println("# error: unknown input file name");
                break;
        }
    }

    public void uploadFileContents(String inputFileName) {
        // Input file which needs to be parsed
        String fileToParse = inputFileName;
        BufferedReader fileReader = null;

        // Delimiter used in CSV file
        final String DELIMITER = ",";
        try {
            String line = "";
            // Create the file reader
            InputStreamReader isr = new InputStreamReader(
                    new FileInputStream(inputFileName));
            fileReader = new BufferedReader(isr);

            // Read the file line by line
            while ((line = fileReader.readLine()) != null) {
                // Get all tokens available in line
                String[] tokens = line.split(DELIMITER);
                processFileContents(inputFileName, tokens);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void designateTerm() {
        if (cycle == 0 || cycle == 1) {
            term = (term + cycle) % 4;
        } else {
            int temp = cycle % 4;
            if (temp == 2) {
                term = 1;
            } else if (temp == 3) {
                term = 2;
            } else if (temp == 0) {
                term = 3;
            } else if (temp == 1) {
                term = 0;
            }
            int temp2 = ((cycle - 2) / 4) + 1;

            year = year + temp2;
        }
    }

    public void assignInstructors(Course course, Instructor instructor) {
        coursesTaught.put(course.getCourseID(), instructor.getID());
        //loadRequests();
    }

    public void loadRequests() {
        String fileToParse = testCaseDirectory.replaceAll("\\\\", "/")
                 + "/requests" + Integer.toString(cycle) + ".csv";
        BufferedReader fileReader = null;
        boolean invalidRequests = false;

        // Delimiter used in CSV file
        final String DELIMITER = ",";
        try {
            String line = "";
            // Create the file reader
            InputStreamReader isr = new InputStreamReader(new FileInputStream(fileToParse));
            fileReader = new BufferedReader(isr);

            // Read the file line by line
            while ((line = fileReader.readLine()) != null) {
                // Get all tokens available in line
                String[] tokens = line.split(DELIMITER);
                if (courses.containsKey(tokens[1]) &&
                        students.containsKey(tokens[0])) {
                    requests.add(new Request(tokens[0], tokens[1]));
                    if (!alreadyTookandPassed(students.get(tokens[0]), tokens[1])) {
                        if (coursesRequested.containsKey(tokens[1])) {
                            coursesRequested.put(tokens[1], coursesRequested.get(tokens[1]) + 1);
                        } else {
                            coursesRequested.put(tokens[1], 1);
                        }
                    }
                } else {
                    invalidRequests = true;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (invalidRequests) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("An Error Occurred!");
            alert.setContentText("Some requests are invalid because of an invalid course id, an invalid student id," +
                    "or the student has already taken and passed the course." +
                    "These requests will be ignored.");
            alert.showAndWait();
            return;
        }
    }

    public void reassignInstructor(String reassignment) {
        String[] tokens = reassignment.split(",");
        coursesTaught.put(tokens[0], tokens[1]);
        processRequests();
    }

    public void processRequests() {
        if (restartedSim) {
            String recordsFile = "Records.csv";
            try {
                BufferedWriter fileWriter = new BufferedWriter(new FileWriter(recordsFile, false));
                fileWriter.write("");
                fileWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (Object key: students.keySet()) {
            students.get(key).getRecords().add(new HashMap<>());
        }
        for (Request request: requests) {
            if (coursesTaught.containsKey(request.getCourseID())) {
                boolean granted = true;
                String studentID = request.getStudentID();
                Student student = students.get(studentID);
                String courseID = request.getCourseID();
                ArrayList<String> prereqs = (ArrayList<String>) courses.get(courseID).getPrereqs();
                for (String id : prereqs) {
                    boolean granted2 = false;
                    boolean granted3 = false;
                    for (int i = 0; i < student.getRecords().size() - 1; i++) {
                        HashMap record = student.getRecords().get(i);
                        if (record.containsKey(id)) {
                            granted3 = true;
                            if (record.get(id).equals("A") || record.get(id).equals("B") || record.get(id).equals("C")) {
                                     granted2 = true;
                            }
                        }
                    }
                    if (!granted3) {
                        request.setResult("Denied - Missing Prereq(s):" +
                                getMissingPrereqs(student, courses.get(courseID)));
                        granted = granted3;
                        break;
                    } else if (!granted2) {
                        request.setResult("Denied - Did not pass prerequisite");
                        granted = granted2;
                        break;
                    }
                }
                if (granted) {
                    request.setResult("Granted");

                    String grade = calculateGrade();
                    student.addCourseRecord(courseID, grade, cycle);

                    String recordsFile = "Records.csv";

                    try {
                        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(recordsFile, true));
                        String record = studentID + "," + courseID + "," + grade + ","
                                + Integer.toString(year) + "," + Integer.toString(term + 1) + "\n";
                        fileWriter.write(record);
                        fileWriter.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            } else {
                request.setResult("Denied - Instructor not assigned to course");
            }
        }
        restartedSim = false;
    }

    public String calculateGrade() {
        double probability = Math.random();
        if (probability < 0.05) {
            return "F";
        } else if (probability < 0.1) {
            return "D";
        } else if (probability < 0.2) {
            return "C";
        } else if (probability < 0.65) {
            return "B";
        } else {
            return "A";
        }
    }

    public String getMissingPrereqs(Student student, Course course) {
        String s = "";
        for (String prereqID : course.getPrereqs()) {
            boolean hasPrereq = false;
            for (int i = 0; i < student.getRecords().size() - 1; i++) {
                HashMap record = student.getRecords().get(i);
                if (record.keySet().contains(prereqID)) {
                    hasPrereq = true;
                }
            }
            if (!hasPrereq) {
                s = s + " " + prereqID + ",";
            }
        }
        return s.substring(0, s.length() - 1);
    }

    public boolean alreadyTookandPassed(Student student, String courseID) {
        boolean temp = false;
        for (int i = 0; i < student.getRecords().size(); i++) {
            HashMap record = student.getRecords().get(i);
            if (record.keySet().contains(courseID)) {
                if (record.get(courseID).equals("A") || record.get(courseID).equals("B") || record.get(courseID).equals("C")) {
                    temp = true;
                }
            }
        }
        return temp;
    }

    public void nextTerm() {
        courseSuggestions = new ArrayList<>();
        for (Object key : coursesRequested.keySet()) {
            for (Object key2 : courses.keySet()) {
                if (courses.get(key2).getPrereqs().contains(key)) {
                    if (!courseSuggestions.contains(key)) {
                        courseSuggestions.add((String) key2);
                    }
                }
            }
        }

        cycle = cycle + 1;
        requests = new ArrayList();
        coursesTaught = new HashMap();
        coursesRequested = new HashMap();
        year = 2017;
        designateTerm();
    }

    public void restartSimulation() {
        this.courses = new HashMap<>();
        this.programs = new HashMap<>();
        this.instructors = new HashMap<>();
        this.students = new HashMap<>();
        this.coursesTaught = new HashMap<>();
        this.cycle = 0;
        this.term = 3;
        this.year = 2017;
        this.terms = new String[]{"Winter", "Spring", "Summer", "Fall"};
        this.requests = new ArrayList<>();
        this.coursesRequested = new HashMap<>();
        this.courseSuggestions = new ArrayList<>();
        this.testCaseDirectory = "";
        this.restartedSim = true;
    }

    public int getCycle() {
        return cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    public HashMap<String, Course> getCourses() {
        return courses;
    }

    public void setCourses(HashMap<String, Course> courses) {
        this.courses = courses;
    }

    public HashMap<String, Program> getPrograms() {
        return programs;
    }

    public void setPrograms(HashMap<String, Program> programs) {
        this.programs = programs;
    }

    public HashMap<String, Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(HashMap<String, Instructor> instructors) {
        this.instructors = instructors;
    }

    public HashMap<String, Student> getStudents() {
        return students;
    }

    public void setStudents(HashMap<String, Student> students) {
        this.students = students;
    }

    public HashMap<String, String> getCoursesTaught() {
        return coursesTaught;
    }

    public Integer getYear() {
        return year;
    }

    public String[] getTerms() {
        return terms;
    }

    public int getTerm() {
        return term;
    }

    public HashMap<String, Integer> getCoursesRequested() {
        return coursesRequested;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public List<String> getCourseSuggestions() {
        return courseSuggestions;
    }

    public void setCourseSuggestions(List<String> courseSuggestions) {
        this.courseSuggestions = courseSuggestions;
    }

    public String getTestCaseDirectory() {
        return testCaseDirectory;
    }

    public void setTestCaseDirectory(String testCaseDirectory) {
        this.testCaseDirectory = testCaseDirectory;
    }
}
