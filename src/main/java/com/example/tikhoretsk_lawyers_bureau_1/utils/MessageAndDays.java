package com.example.tikhoretsk_lawyers_bureau_1.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class MessageAndDays {
    public static final String history = "Наш филиал, из непрерывно действующих, старейший в краснодарском крае, однако история ещё не написана Прасковья Георгиевна  и Игорь Анатольевич исправляют ситуацию!!!.";
    public static final String start = """
            Привет! Данный бот помогает рассчитать размер вознаграждения защитника по назначению.
           
           
             
             
             
            """;
    public static final String textPay = "Логика приложения поддерживает обработку данных с  01 января 2024 года по 30 сентября 2025 ( учтены праздничные и выходные дни и индексация оплаты). В логику приложения не  включено ночное время."
            + System.lineSeparator() + "Выберите подпункта «  » пункта 22.1, Положения о возмещении процессуальных издержек, связанных с производством по уголовному делу…";


    public static final String help = "Судьба, принятие и преображение. Почему важно обратиться за помощью к профессионалу. " +
            "Нередко люди совершают ошибку, обращаясь в суды без предварительной консультации с юристом, самостоятельно составив исковое заявление по образцу, найденному ими в Интернете. " +
            "Последствия такой самостоятельности зачастую являются необратимыми, и юрист, к которому в дальнейшем все-таки приходится обратиться за юридической помощью, " +
            "уже не в силах исправить сложившуюся ситуацию.";


    public final static int[] day2024    = {2359, 2118, 1882, 1646};
    public final static int[] day2025    = {2479, 2226, 1978, 1730};
    public final static int[] dayOff2024 = {3676, 3193, 2722, 2249};
    public final static int[] dayOff2025 = {3863, 3356, 2861, 2364};


    public final static LocalDate[] holidays = {LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 2), LocalDate.of(2024, 1, 3)
            , LocalDate.of(2024, 1, 4), LocalDate.of(2024, 1, 5), LocalDate.of(2024, 1, 6), LocalDate.of(2024, 1, 8)
            , LocalDate.of(2024, 2, 23), LocalDate.of(2024, 3, 8), LocalDate.of(2024, 4, 29), LocalDate.of(2024, 1, 30)
            , LocalDate.of(2024, 5, 1), LocalDate.of(2024, 5, 9), LocalDate.of(2024, 5, 10), LocalDate.of(2024, 6, 12)
            , LocalDate.of(2024, 11, 4), LocalDate.of(2024, 12, 29), LocalDate.of(2024, 12, 30),


            LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 2), LocalDate.of(2025, 1, 3), LocalDate.of(2025, 1, 4)
            , LocalDate.of(2024, 1, 5), LocalDate.of(2025, 1, 6), LocalDate.of(2025, 1, 7), LocalDate.of(2025, 1, 8)
            , LocalDate.of(2025, 2, 23), LocalDate.of(2025, 3, 8), LocalDate.of(2025, 4, 29), LocalDate.of(2025, 1, 30)
            , LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 9), LocalDate.of(2025, 5, 10), LocalDate.of(2025, 6, 12)
            , LocalDate.of(2025, 11, 4), LocalDate.of(2025, 12, 29), LocalDate.of(2025, 12, 30)


    };
    public static String goodMorning(){
        ArrayList<String> goodMornings=new ArrayList<>();
        goodMornings.add(" Доброе утро! Не знал, что такое время бывает дважды в день."); goodMornings.add("Пора вставать! Кофеварка уже тебя скучает."); goodMornings.add(" Доброе утро! Пусть твой кофе сегодня будет крепким, а работа - короткой.");
        goodMornings.add("Пора просыпаться! Много дел... Позже."); goodMornings.add("   Доброе утро! Время встать и делать вид, что у нас все под контролем."); goodMornings.add("Желаю тебе хорошего утра! А если нет, то ложись обратно.");
        goodMornings.add("Завтрак готов! Ну, почти. Тебе осталось только сходить за покупками, приготовить еду и накрыть на стол."); goodMornings.add("Утро! Сегодня опять один из тех дней, когда нужно улыбнуться... прежде чем кого-то убить.");
        goodMornings.add("Доброе утро! Пусть твой день будет столь же структурированным, как мои воскресенья... полностью бесплановым."); goodMornings.add(""); goodMornings.add(""); goodMornings.add(""); goodMornings.add("");
        goodMornings.add("Пора вставать! Мир ждет твоего света и хаоса."); goodMornings.add("Доброе утро! Пусть твой день будет таким освежающим, как холодный душ."); goodMornings.add("  Зачем вставать? Мир принадлежит тем, кто встает поздно и все равно успевает сделать все."); goodMornings.add("Доброе утро! Давайте делать вид, что мы работали всю ночь.");
        goodMornings.add(" Доброе утро! Пора покинуть постель и завоевать диван."); goodMornings.add("Просыпайся! Твои мечты замечательные, но и завтрак неплох."); goodMornings.add("Утро! Если бы сон был олимпийским видом спорта, у нас было бы золото.");
        Collections.shuffle(goodMornings);
    return goodMornings.get(0);}





    public  static  final  Long[] chat_id={434737035L, 846878161L, 406517766L, 803901509L, 1383650677L, 1052145142L, 5328965521L};





}


