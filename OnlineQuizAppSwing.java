import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class Question {
    private String questionText;
    private String[] options;
    private int correctOption;

    public Question(String questionText, String[] options, int correctOption) {
        this.questionText = questionText;
        this.options = options;
        this.correctOption = correctOption;
    }

    public boolean isCorrect(int selectedOption) {
        return selectedOption == correctOption;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }
}

class Quiz {
    ArrayList<Question> questions;
    private int currentQuestionIndex;
    private int userScore;

    public Quiz(ArrayList<Question> questions) {
        this.questions = questions;
        this.currentQuestionIndex = 0;
        this.userScore = 0;
    }

    public Question getCurrentQuestion() {
        return questions.get(currentQuestionIndex);
    }

    public boolean hasNextQuestion() {
        return currentQuestionIndex < questions.size() - 1;
    }

    public void nextQuestion() {
        if (hasNextQuestion()) {
            currentQuestionIndex++;
        }
    }

    public int getUserScore() {
        return userScore;
    }

    public void incrementUserScore() {
        userScore++;
    }
}

public class OnlineQuizAppSwing {
    private static JFrame frame;
    private static JPanel panel;
    private static JLabel questionLabel;
    private static ButtonGroup buttonGroup;
    private static JRadioButton[] optionButtons;
    private static JButton nextButton;
    private static JLabel scoreLabel;

    private static Quiz quiz;

    public static void main(String[] args) {
        // Create questions
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question("What is the capital of Japan?", new String[]{"Seoul", "Beijing", "Tokyo", "Bangkok"}, 2));
        questions.add(new Question("Which programming language is known as the 'mother of all languages'?", new String[]{"Java", "Python", "C", "Assembly"}, 2));
        // Add more questions here...
        questions.add(new Question("What is the largest planet in our solar system?", new String[]{"Mars", "Jupiter", "Venus", "Saturn"}, 1));
        questions.add(new Question("Which of the following is a front-end framework?", new String[]{"Spring", "Angular", "Hibernate", "Node.js"}, 1));
        questions.add(new Question("What is the main purpose of an 'if' statement in programming?", new String[]{"Looping", "Conditional Execution", "Declaration", "Initialization"}, 1));
        questions.add(new Question("Which data type is used to represent whole numbers in Java?", new String[]{"float", "double", "int", "char"}, 2));
        questions.add(new Question("Who is the author of 'Romeo and Juliet'?", new String[]{"Charles Dickens", "Jane Austen", "William Shakespeare", "Mark Twain"}, 2));
        questions.add(new Question("Which of the following is a relational database management system?", new String[]{"MongoDB", "MySQL", "Redis", "Elasticsearch"}, 1));
        questions.add(new Question("What is the capital of Australia?", new String[]{"Sydney", "Melbourne", "Canberra", "Brisbane"}, 2));
        questions.add(new Question("Which planet is known as the 'Red Planet'?", new String[]{"Mars", "Jupiter", "Venus", "Saturn"}, 0));
        // Create a quiz
        quiz = new Quiz(questions);

        // Create and configure the GUI
        frame = new JFrame("Extended Quiz App");
        panel = new JPanel();
        questionLabel = new JLabel();
        buttonGroup = new ButtonGroup();
        optionButtons = new JRadioButton[4];
        nextButton = new JButton("Next");
        scoreLabel = new JLabel("Score: 0");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        panel.setLayout(new GridLayout(6, 1));
        panel.add(scoreLabel);
        panel.add(questionLabel);

        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i] = new JRadioButton();
            buttonGroup.add(optionButtons[i]);
            panel.add(optionButtons[i]);
        }

        panel.add(nextButton);

        frame.add(panel, BorderLayout.CENTER);

        // Display the first question
        displayQuestion();

        // Add action listener to the Next button
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
                if (quiz.hasNextQuestion()) {
                    quiz.nextQuestion();
                    displayQuestion();
                } else {
                    endQuiz();
                }
            }
        });

        // Set the frame visibility
        frame.setVisible(true);
    }

    private static void displayQuestion() {
        Question currentQuestion = quiz.getCurrentQuestion();
        questionLabel.setText(currentQuestion.getQuestionText());

        String[] options = currentQuestion.getOptions();
        for (int i = 0; i < options.length; i++) {
            optionButtons[i].setText(options[i]);
            optionButtons[i].setSelected(false);
        }
    }

    private static void checkAnswer() {
        Question currentQuestion = quiz.getCurrentQuestion();
        for (int i = 0; i < optionButtons.length; i++) {
            if (optionButtons[i].isSelected() && currentQuestion.isCorrect(i)) {
                quiz.incrementUserScore();
                break;
            }
        }
        updateScoreLabel();
    }

    private static void updateScoreLabel() {
        scoreLabel.setText("Score: " + quiz.getUserScore());
    }

    private static void endQuiz() {
        JOptionPane.showMessageDialog(frame, "Quiz completed. Your score: " + quiz.getUserScore() + "/" + quiz.questions.size());
        System.exit(0);
    }
}
