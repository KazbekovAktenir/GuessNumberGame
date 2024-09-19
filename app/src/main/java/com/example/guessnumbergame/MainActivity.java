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
    private int attemptsCount; // Переменная для подсчета попыток
    private final int maxAttempts = 9; // Максимальное количество попыток
    private EditText numberInput;
    private TextView resultText;
    private TextView attemptsText; // Переменная для отображения количества попыток
    private Button guessButton;
    private Button restartButton; // Кнопка "Играть заново"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация переменных
        numberInput = findViewById(R.id.number_input);
        resultText = findViewById(R.id.result_text);
        guessButton = findViewById(R.id.guess_button);
        attemptsText = findViewById(R.id.attempts_text); // Инициализация TextView для попыток
        restartButton = findViewById(R.id.restart_button); // Инициализация кнопки "Играть заново"

        // Генерация случайного числа
        generateRandomNumber();

        // Обработчик нажатия кнопки "Угадать"
        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGuess();
            }
        });

        // Обработчик нажатия кнопки "Играть заново"
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartGame();
            }
        });
    }

    // Метод для генерации случайного числа и сброса счетчика попыток
    private void generateRandomNumber() {
        Random random = new Random();
        randomNumber = random.nextInt(100) + 1; // Число от 1 до 100
        attemptsCount = 0; // Сброс счетчика попыток
        updateAttemptsText(); // Обновляем количество попыток в интерфейсе
        guessButton.setEnabled(true); // Включаем кнопку для новой игры
        restartButton.setVisibility(View.GONE); // Прячем кнопку "Играть заново"
        numberInput.setText(""); // Очищаем поле ввода
        resultText.setText(""); // Очищаем текст результата
    }

    // Метод для перезапуска игры
    private void restartGame() {
        generateRandomNumber();
    }

    // Обновление текста с количеством попыток
    private void updateAttemptsText() {
        attemptsText.setText("Количество попыток: " + attemptsCount + "/" + maxAttempts);
    }

    // Проверка числа, введенного пользователем
    private void checkGuess() {
        String input = numberInput.getText().toString();

        if (input.isEmpty()) {
            resultText.setText("Введите число!");
            return;
        }

        int guessedNumber = Integer.parseInt(input);

        // Проверка, что введенное число в пределах от 1 до 100
        if (guessedNumber < 1 || guessedNumber > 100) {
            resultText.setText("Число должно быть в диапазоне от 1 до 100!");
            return;
        }

        attemptsCount++; // Увеличение количества попыток
        updateAttemptsText(); // Обновляем количество попыток

        if (guessedNumber < randomNumber) {
            resultText.setText("Больше!");
        } else if (guessedNumber > randomNumber) {
            resultText.setText("Меньше!");
        } else {
            // Сообщение о правильном угадывании с количеством попыток
            resultText.setText("Поздравляем! Вы угадали число с " + attemptsCount + " попытки!");
            guessButton.setEnabled(false); // Отключаем кнопку "Угадать" после победы
            restartButton.setVisibility(View.VISIBLE); // Показываем кнопку "Играть заново"
            return;
        }

        // Проверка на лимит попыток
        if (attemptsCount >= maxAttempts) {
            resultText.setText("Вы проиграли! Правильное число: " + randomNumber);
            guessButton.setEnabled(false); // Отключаем кнопку после проигрыша
            restartButton.setVisibility(View.VISIBLE); // Показываем кнопку "Играть заново"
        }
    }
}
