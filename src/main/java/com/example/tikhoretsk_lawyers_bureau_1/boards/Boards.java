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
import java.util.List;

@Component
@RequiredArgsConstructor
public class Boards {
    private final AppUserRepository appUserRepository;

    public SendMessage startKeyboardAb(long chat_id) {
        appUserRepository.save(chat_id);
        SendMessage message = new SendMessage();
        message.setChatId(chat_id);
        message.setText("Тихорецкий филиал № 1  Краснодарской Коллегии адвокатов адвокатской  палаты Краснодарского края приветствует вас!!!");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
        InlineKeyboardButton button = buttonGreat("Адвокатская палата Краснодарского края", "saitSait");
        button.setUrl("https://apkk.ru/");
        InlineKeyboardButton button1 = buttonGreat("НАШ АДРЕС", "sait");
        button1.setUrl("https://www.google.com/maps/place/%D1%83%D0%BB.+%D0%AD%D0%BD%D0%B3%D0%B5%D0%BB%D1%8C%D1%81%D0%B0,+174,+%D0%A2%D0%B8%D1%85%D0%BE%D1%80%D0%B5%D1%86%D0%BA,+%D0%9A%D1%80%D0%B0%D1%81%D0%BD%D0%BE%D0%B4%D0%B0%D1%80%D1%81%D0%BA%D0%B8%D0%B9+%D0%BA%D1%80%D0%B0%D0%B9,+352120/@45.8573395,40.1215312,19z/data=!4m6!3m5!1s0x40fb1fbb77b46029:0x8dfa863ee15c0b2b!8m2!3d45.857787!4d40.122075!16s%2Fg%2F11dfj0wtwq?entry=ttu&g_ep=EgoyMDI0MTAwMi4xIKXMDSoASAFQAw%3D%3D");


        rowInline1.add(button);
        rowInline1.add(button1);

        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();

        InlineKeyboardButton button2 = buttonGreat("НАШИ АДВОКАТЫ", "LR");
        InlineKeyboardButton button3 = buttonGreat("Образцы заявлений", "idea");

        rowInline2.add(button2);
        rowInline2.add(button3);


        List<InlineKeyboardButton> rowInline11 = new ArrayList<>();
        List<InlineKeyboardButton> rowInline22 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton21 = buttonGreat(" Расчет размера оплаты труда адвоката по назначению", "Размер оплаты труда");
        InlineKeyboardButton inlineKeyboardButton22 = buttonGreat("НАША ИСТОРИЯ", "hist");


        rowInline11.add(inlineKeyboardButton21);
        rowInline22.add(inlineKeyboardButton22);
        rowsInline.add(rowInline22);
        rowsInline.add(rowInline1);
        rowsInline.add(rowInline2);

        rowsInline.add(rowInline11);

        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);

        return message;

    }

    public SendMessage defenders(long chat_id) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();//2
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        InlineKeyboardButton button = buttonGreat("ПРОШИНА П.Г.", "pr");
        InlineKeyboardButton button1 = buttonGreat("МОРОЗОВ И.А.", "mo");
        buttons.add(button);
        buttons.add(button1);
        InlineKeyboardButton button2 = buttonGreat("СТЕПАНЕНКО В.Ю.", "ст");
        InlineKeyboardButton button3 = buttonGreat("ЧУРИЛОВ А.П.", "чу");
        List<InlineKeyboardButton> buttons2 = new ArrayList<>();
        buttons2.add(button2);
        buttons2.add(button3);
        InlineKeyboardButton button4 = buttonGreat("ЗАХАРЬЕВА Т.И.", "за");
        InlineKeyboardButton button5 = buttonGreat("ПАРУШЕВА Е.В.", "па");
        List<InlineKeyboardButton> buttons3 = new ArrayList<>();
        buttons3.add(button4);
        buttons3.add(button5);

        InlineKeyboardButton button6 = buttonGreat("ЧМЫХ П.С.", "чм");
        InlineKeyboardButton button7 = buttonGreat("КАРНАЧЕВА А.В.", "ка");
        List<InlineKeyboardButton> buttons4 = new ArrayList<>();
        buttons4.add(button6);
        buttons4.add(button7);
        InlineKeyboardButton button8 = buttonGreat("ДЕРГАЧ Д.Б.", "де");
        InlineKeyboardButton button9 = buttonGreat("КАЗАРЯН Е.М.", "кз");
        List<InlineKeyboardButton> buttons5 = new ArrayList<>();
        buttons5.add(button8);
        buttons5.add(button9);
        InlineKeyboardButton button10 = buttonGreat("ШЕВЕЛЁВА И.В.", "ше");
        InlineKeyboardButton button11 = buttonGreat("ВААГН ДАВТЯН", "да");
        List<InlineKeyboardButton> buttons6 = new ArrayList<>();
        buttons6.add(button10);
        buttons6.add(button11);
        InlineKeyboardButton button12 = buttonGreat("ЖДАНОВ А.В.", "жд");
        InlineKeyboardButton button13 = buttonGreat("ТЮРИНА И.В.", "тю");

        List<InlineKeyboardButton> buttons7 = new ArrayList<>();
        buttons7.add(button12);
        buttons7.add(button13);

        InlineKeyboardButton button14 = buttonGreat("АЛЕКСАНДР БУРОВ", "сш");
        InlineKeyboardButton button15 = buttonGreat("СОФИЯ", "соф");
        button14.setUrl("https://ru.wikipedia.org/wiki/%D0%9B%D0%B5%D0%B2");
        button15.setUrl("https://ru.wikipedia.org/wiki/%D0%A1%D0%BE%D1%84%D0%B8%D1%8F_(%D1%84%D0%B8%D0%BB%D0%BE%D1%81%D0%BE%D1%84%D0%B8%D1%8F)");
        List<InlineKeyboardButton> buttons8 = new ArrayList<>();
        buttons8.add(button14);
        buttons8.add(button15);
        InlineKeyboardButton button16 = buttonGreat("КАТАСОНОВ Р.П.", "кат");
        InlineKeyboardButton button17 = buttonGreat("КУШНИРОВА Д.А.", "куш");
        button17.setUrl("https://ru.wikipedia.org/wiki/%D0%9E%D1%82%D0%BF%D1%83%D1%81%D0%BA_%D0%BF%D0%BE_%D0%B1%D0%B5%D1%80%D0%B5%D0%BC%D0%B5%D0%BD%D0%BD%D0%BE%D1%81%D1%82%D0%B8_%D0%B8_%D1%80%D0%BE%D0%B4%D0%B0%D0%BC");
        button16.setUrl("https://github.com/Katas77/tikhoretsk-lawyers-bureau-1");

        List<InlineKeyboardButton> buttons9 = new ArrayList<>();
        buttons9.add(button16);
        buttons9.add(button17);

        rowsInline.add(buttons);
        rowsInline.add(buttons2);
        rowsInline.add(buttons3);
        rowsInline.add(buttons4);
        rowsInline.add(buttons5);
        rowsInline.add(buttons6);
        rowsInline.add(buttons7);
        rowsInline.add(buttons8);
        rowsInline.add(buttons9);
        return messageGreat("Всегда помогут", rowsInline, chat_id);
    }

    public SendMessage paragraphs(long chat_id) {
        appUserRepository.newDays(chat_id);
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();//2
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        InlineKeyboardButton button1 = buttonGreat("'А'", "a");
        InlineKeyboardButton button2 = buttonGreat("'Б'", "b");
        InlineKeyboardButton button3 = buttonGreat("'В'", "v");
        InlineKeyboardButton button4 = buttonGreat("'Г'", "g");
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        rowsInline.add(buttons);

        return messageGreat(MessageAndDays.textPay, rowsInline, chat_id);
    }

    public SendMessage nextFinish(long chat_id) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();//2
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        InlineKeyboardButton button1 = buttonGreat("НОВАЯ ДАТА", "но");
        InlineKeyboardButton button2 = buttonGreat("ЗАКОНЧИТЬ", "законч");

        buttons.add(button1);
        buttons.add(button2);

        rowsInline.add(buttons);
        if (appUserRepository.findByIdAppUser(chat_id).orElseThrow().getParagraph() == null) {
            return paragraphs(chat_id);
        }
        return messageGreat("ВЫБЕРЕТЕ", rowsInline, chat_id);
    }

    public SendMessage quarter(long chat_id) {
        if (appUserRepository.findByIdAppUser(chat_id).orElseThrow().getParagraph() == null) {
            return paragraphs(chat_id);
        }
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();//2
        List<InlineKeyboardButton> buttons1 = Arrays.asList(buttonGreat("ЯНВАРЬ ФЕВРАЛЬ МАРТ 2024", "quarter_1_24"));
        List<InlineKeyboardButton> buttons2 = Arrays.asList(buttonGreat("АПРЕЛЬ МАРТ ИЮНЬ 2024", "quarter_2_24"));
        List<InlineKeyboardButton> buttons3 = Arrays.asList(buttonGreat("ИЮЛЬ АВГУСТ СЕНТЯБРЬ 2024", "quarter_3_24"));
        List<InlineKeyboardButton> buttons4 = Arrays.asList(buttonGreat("ОКТЯБРЬ НОЯБРЬ ДЕКАБРЬ 2024", "quarter_4_24"));
        List<InlineKeyboardButton> buttons5 = Arrays.asList(buttonGreat("ЯНВАРЬ ФЕВРАЛЬ МАРТ 2025", "quarter_1_25"));
        List<InlineKeyboardButton> buttons6 = Arrays.asList(buttonGreat("АПРЕЛЬ МАРТ ИЮНЬ 2025", "quarter_2_25"));
        List<InlineKeyboardButton> buttons7 = Arrays.asList(buttonGreat("ИЮНЬ АВГУСТ СЕНТЯБРЬ 2025", "quarter_3_25"));
        rowsInline.add(buttons1);
        rowsInline.add(buttons2);
        rowsInline.add(buttons3);
        rowsInline.add(buttons4);
        rowsInline.add(buttons5);
        rowsInline.add(buttons6);
        rowsInline.add(buttons7);

        return messageGreat("ВЫБЕРЕТЕ КВАРТАЛ", rowsInline, chat_id);
    }

    public InlineKeyboardButton buttonGreat(String text, String backData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(backData);
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
