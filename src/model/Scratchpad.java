package model;
/**
 * Created by Ashwin Ignatius on 11/4/2017.
 */

import java.io.*;
import java.util.*;

public class Scratchpad {
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
    }

    private void processFileContents(String inputFileName, String[] tokens) {
        // REMOVE THESE PRINT STATEMENTS BEFORE SUBMISSION
        // display the contents (token) in the line selected for processing
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
        // REMOVE THIS PRINT STATEMENT BEFORE SUBMISSION
        // display the name of the file selected for processing
        System.out.println(inputFileName);

        // Input file which needs to be parsed
        String fileToParse = inputFileName;
        BufferedReader fileReader = null;

        // Delimiter used in CSV file
        final String DELIMITER = ",";
        try {
            String line = "";
            // Create the file reader
            fileReader = new BufferedReader(new FileReader(fileToParse));

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
        System.out.println(term + " " + year);
    }

    public void assignInstructors(Course course, Instructor instructor) {
        coursesTaught.put(course.getCourseID(), instructor.getID());
        //loadRequests();
    }

    public void loadRequests() {
        String fileToParse = "./TestCases/test_case1/requests"+ Integer.toString(cycle) + ".csv";
        BufferedReader fileReader = null;

        // Delimiter used in CSV file
        final String DELIMITER = ",";
        try {
            String line = "";
            // Create the file reader
            fileReader = new BufferedReader(new FileReader(fileToParse));

            // Read the file line by line
            while ((line = fileReader.readLine()) != null) {
                // Get all tokens available in line
                String[] tokens = line.split(DELIMITER);

                requests.add(new Request(tokens[0], tokens[1]));
                if (coursesRequested.containsKey(tokens[1])) {
                    coursesRequested.put(tokens[1], coursesRequested.get(tokens[1]) + 1);
                } else {
                    coursesRequested.put(tokens[1], 1);
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
    }

    public void reassignInstructor(String reassignment) {
        String[] tokens = reassignment.split(",");
        for (Object key: coursesTaught.keySet()) {
            if (coursesTaught.get(key).equals(tokens[1])) {
                coursesTaught.remove(key);
                coursesTaught.put(tokens[0], tokens[1]);
                break;
            }
        }
        processRequests();
    }

    public void processRequests() {
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
                    for (HashMap record : student.getRecords()) {
                        if (record.containsKey(id)) {
                                 if (record.get(id) == "A" || record.get(id) == "B" || record.get(id) == "C") {
                                     granted2 = true;
                                 } else {
                                     request.setResult("Denied - Did not pass prerequisite");
                                 }
                        } else {
                            request.setResult("Denied - Missing Prerequisite");
                        }
                    }
                    if (!granted2) {
                        granted = granted2;
                        break;
                    }
                }
                if (granted) {
                    request.setResult("Granted");

                    String grade = calculateGrade();
                    student.addCourseRecord(courseID, grade, cycle);

                    String recordsFile = "./src/fxapp/Records.csv";

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
    }

    public void nextTerm() {
        cycle = cycle + 1;
        requests = new ArrayList();
        coursesTaught = new HashMap();
        coursesRequested = new HashMap();
        year = 2017;
        designateTerm();
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
}
