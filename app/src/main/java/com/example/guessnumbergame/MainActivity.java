package com.example.guessnumbergame;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int randomNumber;
    private int attemptsCount; //переменная для подсчета попыток
    private final int maxAttempts = 9; //максимальное количество попыток
    private EditText numberInput;
    private TextView resultText;
    private TextView attemptsText; //переменная для отображения количества попыток
    private Button guessButton;
    private Button restartButton; //кнопка "Играть заново"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //инициализация переменных
        numberInput = findViewById(R.id.number_input);
        resultText = findViewById(R.id.result_text);
        guessButton = findViewById(R.id.guess_button);
        attemptsText = findViewById(R.id.attempts_text); //инициализация TextView для попыток
        restartButton = findViewById(R.id.restart_button); //инициализация кнопки "Играть заново"

        //генерация случайного числа
        generateRandomNumber();

        //обработчик нажатия кнопки "Угадать"
        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGuess();
            }
        });

        //обработчик нажатия кнопки "Играть заново"
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartGame();
            }
        });
    }

    //метод для генерации случайного числа и сброса счетчика попыток
    private void generateRandomNumber() {
        Random random = new Random();
        randomNumber = random.nextInt(100) + 1; //число от 1 до 100
        attemptsCount = 0; //сброс счетчика попыток
        updateAttemptsText(); //обновляем количество попыток в интерфейсе
        guessButton.setEnabled(true); //включаем кнопку для новой игры
        restartButton.setVisibility(View.GONE); //прячем кнопку "Играть заново"
        numberInput.setText(""); //очищаем поле ввода
        resultText.setText(""); //очищаем текст результата
    }

    //метод для перезапуска игры
    private void restartGame() {
        generateRandomNumber();
    }

    //обновление текста с количеством попыток
    private void updateAttemptsText() {
        attemptsText.setText("Количество попыток: " + attemptsCount + "/" + maxAttempts);
    }

    //проверка числа, введенного пользователем
    private void checkGuess() {
        String input = numberInput.getText().toString();

        if (input.isEmpty()) {
            resultText.setText("Введите число!");
            return;
        }

        int guessedNumber = Integer.parseInt(input);

        //проверка, что введенное число в пределах от 1 до 100
        if (guessedNumber < 1 || guessedNumber > 100) {
            resultText.setText("Число должно быть в диапазоне от 1 до 100!");
            return;
        }

        attemptsCount++; //увеличение количества попыток
        updateAttemptsText(); //обновляем количество попыток

        if (guessedNumber < randomNumber) {
            resultText.setText("Больше!");
        } else if (guessedNumber > randomNumber) {
            resultText.setText("Меньше!");
        } else {
            //сообщение о правильном угадывании с количеством попыток
            resultText.setText("Поздравляем! Вы угадали число с " + attemptsCount + " попытки!");
            guessButton.setEnabled(false); //отключаем кнопку "Угадать" после победы
            restartButton.setVisibility(View.VISIBLE); //показываем кнопку "Играть заново"
            return;
        }

        //проверка на лимит попыток
        if (attemptsCount >= maxAttempts) {
            resultText.setText("Вы проиграли! Правильное число: " + randomNumber);
            guessButton.setEnabled(false); //отключаем кнопку после проигрыша
            restartButton.setVisibility(View.VISIBLE); //показываем кнопку "Играть заново"
        }
    }
}
