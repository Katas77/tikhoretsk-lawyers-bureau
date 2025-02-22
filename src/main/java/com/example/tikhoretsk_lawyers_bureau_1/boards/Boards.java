package com.example.tikhoretsk_lawyers_bureau_1.boards;

import com.example.tikhoretsk_lawyers_bureau_1.utils.MessageAndDays;
import com.example.tikhoretsk_lawyers_bureau_1.database.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Boards {
    private final AppUserRepository appUserRepository;

    public SendMessage startKeyboardAb(long chat_id) {
        appUserRepository.save(chat_id);

        String welcomeMessage = "Тихорецкий филиал № 1  Краснодарской Коллегии адвокатов адвокатской  палаты Краснодарского края приветствует вас!!!";
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();


        rowsInline.add(createButtonRow(
                createCustomButton("Адвокатская палата Краснодарского края", "saitSait", "https://apkk.ru/"),
                createCustomButton("НАШ АДРЕС", "sait", "https://www.google.com/maps/place/%D1%83%D0%BB.+%D0%AD%D0%BD%D0%B3%D0%B5%D0%BB%D1%8C%D1%81%D0%B0,+174,+%D0%A2%D0%B8%D1%85%D0%BE%D1%80%D0%B5%D1%86%D0%BA,+%D0%9A%D1%80%D0%B0%D1%81%D0%BD%D0%BE%D0%B4%D0%B0%D1%80%D1%81%D0%BA%D0%B8%D0%B9+%D0%BA%D1%80%D0%B0%D0%B9,+352120/@45.8573395,40.1215312,19z/data=!4m6!3m5!1s0x40fb1fbb77b46029:0x8dfa863ee15c0b2b!8m2!3d45.857787!4d40.122075!16s%2Fg%2F11dfj0wtwq?entry=ttu&g_ep=EgoyMDI0MTAwMi4xIKXMDSoASAFQAw%3D%3D")
        ));

        rowsInline.add(createButtonRow(
                createCustomButton("НАШИ АДВОКАТЫ", "LR"),
                createCustomButton("Образцы заявлений", "idea")
        ));

        rowsInline.add(createButtonRow(
                createCustomButton("Расчет размера оплаты труда адвоката по назначению", "Размер оплаты труда"),
                createCustomButton("НАША ИСТОРИЯ", "hist")
        ));

        return messageGreat(welcomeMessage, rowsInline, chat_id);

    }

    public SendMessage defenders(long chatId) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        // Create button rows using helper methods
        rowsInline.add(createButtonRow(
                buttonGreatStars("ПРОШИНА П.Г.", "pr"),
                buttonGreatStars("МОРОЗОВ И.А.", "mo")
        ));

        rowsInline.add(createButtonRow(
                createCustomButton("СТЕПАНЕНКО В.Ю.", "ст"),
                createCustomButton("ЧУРИЛОВ А.П.", "чу")
        ));

        rowsInline.add(createButtonRow(
                buttonGreatRose("ЗАХАРЬЕВА Т.И.", "за"),
                createCustomButton("ПАРУШЕВА Е.В.", "па")
        ));

        rowsInline.add(createButtonRow(
                createCustomButton("ЧМЫХ П.С.", "чм"),
                createCustomButton("КАРНАЧЕВА А.В.", "ка")
        ));

        rowsInline.add(createButtonRow(
                createCustomButton("ДЕРГАЧ Д.Б.", "де"),
                createCustomButton("КАЗАРЯН Е.М.", "кз")
        ));

        rowsInline.add(createButtonRow(
                createCustomButton("ШЕВЕЛЁВА И.В.", "ше"),
                createCustomButton("ВААГН ДАВТЯН", "да")
        ));

        rowsInline.add(createButtonRow(
                createCustomButton("ЖДАНОВ А.В.", "жд"),
                createCustomButton("ТЮРИНА И.В.", "тю")
        ));

        // Adding buttons with URLs
        rowsInline.add(createButtonRow(
                createCustomButton("АЛЕКСАНДР БУРОВ", "сш", "https://ru.wikipedia.org/wiki/%D0%9B%D0%B5%D0%B2"),
                createCustomButton("СОФИЯ", "соф", "https://ru.wikipedia.org/wiki/%D0%A1%D0%BE%D1%84%D0%B8%D1%8F_(%D1%84%D0%B8%D0%BB%D0%BE%D1%81%D0%BE%D1%84%D0%B8%D1%8F)")
        ));

        rowsInline.add(createButtonRow(
                createCustomButton("КАТАСОНОВ Р.П.", "кат", "https://github.com/Katas77/tikhoretsk-lawyers-bureau-1"),
                createCustomButton("КУШНИРОВА Д.А.", "куш", "https://ru.wikipedia.org/wiki/%D0%9E%D1%82%D0%BF%D1%83%D1%81%D0%BA_%D0%BF%D0%BE_%D0%B1%D0%B5%D1%80%D0%B5%D0%BC%D0%B5%D0%BD%D0%BD%D0%BE%D1%81%D1%82%D0%B8_%D0%B8_%D1%80%D0%BE%D0%B4%D0%B0%D0%BC")
        ));

        return messageGreat("Всегда помогут и поддержат в трудную минуту:", rowsInline, chatId);
    }


    public SendMessage paragraphs(long chat_id) {
        appUserRepository.newDays(chat_id);
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(createButtonRow(
                createCustomButton("'А'", "a"),
                createCustomButton("'Б'", "b"),
                createCustomButton("'В'", "v"),
                createCustomButton("'Г'", "g")
        ));
        return messageGreat(MessageAndDays.textPay, rowsInline, chat_id);
    }

    public SendMessage nextFinish(long chat_id) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();//2
        rowsInline.add(createButtonRow(
                createCustomButton("НОВАЯ ДАТА", "но"),
                createCustomButton("ЗАКОНЧИТЬ", "законч")
        ));
        if (appUserRepository.findByIdAppUser(chat_id).orElseThrow().getParagraph() == null) {
            return paragraphs(chat_id);
        }
        return messageGreat("ВЫБЕРЕТЕ", rowsInline, chat_id);
    }

    public SendMessage quarter(long chat_id) {
        if (appUserRepository.findByIdAppUser(chat_id).orElseThrow().getParagraph() == null) {
            return paragraphs(chat_id);
        }
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        rowsInline.add(createButtonRow("ЯНВАРЬ ФЕВРАЛЬ МАРТ 2024", "quarter_1_24"));
        rowsInline.add(createButtonRow("АПРЕЛЬ МАРТ ИЮНЬ 2024", "quarter_2_24"));
        rowsInline.add(createButtonRow("ИЮЛЬ АВГУСТ СЕНТЯБРЬ 2024", "quarter_3_24"));
        rowsInline.add(createButtonRow("ОКТЯБРЬ НОЯБРЬ ДЕКАБРЬ 2024", "quarter_4_24"));
        rowsInline.add(createButtonRow("ЯНВАРЬ ФЕВРАЛЬ МАРТ 2025", "quarter_1_25"));
        rowsInline.add(createButtonRow("АПРЕЛЬ МАРТ ИЮНЬ 2025", "quarter_2_25"));
        rowsInline.add(createButtonRow("ИЮНЬ АВГУСТ СЕНТЯБРЬ 2025", "quarter_3_25"));

        return messageGreat("ВЫБЕРЕТЕ КВАРТАЛ", rowsInline, chat_id);
    }

    public InlineKeyboardButton buttonGreatRose(String text, String backData) {
        return createCustomButton("🌺 " + text + " 🌺", backData);
    }

    public InlineKeyboardButton buttonGreatStars(String text, String backData) {
        return createCustomButton("⭐ " + text + " ⭐", backData);
    }

    private List<InlineKeyboardButton> createButtonRow(String text, String backData) {
        return Collections.singletonList(createCustomButton(text, backData));
    }

    private List<InlineKeyboardButton> createButtonRow(InlineKeyboardButton... buttons) {
        return new ArrayList<>(Arrays.asList(buttons));
    }


    public InlineKeyboardButton createCustomButton(String text, String backData) {
        return createCustomButton(text, backData, null);
    }

    public InlineKeyboardButton createCustomButton(String text, String backData, String url) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(backData);
        if (url != null) {
            button.setUrl(url);
        }
        return button;
    }

    public SendMessage messageGreat(String text, List<List<InlineKeyboardButton>> rowsInline, long chat_id) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInline);
        SendMessage message = new SendMessage();
        message.setChatId(chat_id);
        message.setText(text);
        message.setParseMode(ParseMode.HTML);
        message.setReplyMarkup(markupInline);
        return message;
    }

}
/**
 * Примеры эмодзи для украшения текста кнопок:
 * Сердце: ❤️
 * Звезда: ⭐
 * Флаг: 🚩
 * Галочка: ✅
 * Вопросительный знак: ⁉️
 * Смех: 😂
 * Аплодисменты: 👏
 * Подарок: 🎁
 * Планета Земля: 🌍
 * Компьютер: 💻
 */