package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MyBot extends TelegramLongPollingBot {

    public MyBotService myBotService;

    public MyBot(MyBotService myBotService) {
        this.myBotService = myBotService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            handleMessage(update);
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update);
        }
    }

    private void handleMessage(Update update) {
        String message = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        String firstname = update.getMessage().getChat().getFirstName();
        String username = update.getMessage().getChat().getUserName();

        info(chatId, firstname, username, message);

        switch (message) {
            case "/start":
                try {
                    execute(myBotService.sendWelcomeMessage(chatId));
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "/photo":
                try {
                    execute(myBotService.sendPhoto(chatId));
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "🛍️ Order":
                try {
                    execute(myBotService.showCategories(chatId));
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "📨 Vacancies":
                try {
                    execute(myBotService.showVacancies(chatId));
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "📍 Location":
                try {
                    execute(myBotService.sendLocation(chatId));
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "📋 View Cart":
                try {
                    execute(myBotService.viewCart(chatId));
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }

    private void handleCallbackQuery(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        Message message = (Message) update.getCallbackQuery().getMessage();

        try {
            if (callbackData.startsWith("category_")) {
                String category = callbackData.substring(9);
                execute(myBotService.showCategoryItems(chatId, category));
            } else if (callbackData.startsWith("add_")) {
                String[] parts = callbackData.substring(4).split("_");
                String itemId = parts[0];
                int quantity = Integer.parseInt(parts[1]);
                execute(myBotService.addToCart(chatId, itemId, quantity, message.getMessageId()));
            } else if (callbackData.startsWith("cart_")) {
                String action = callbackData.substring(5);
                execute(myBotService.handleCartAction(chatId, action, message.getMessageId()));
            } else if (callbackData.startsWith("remove_")) {
                String itemId = callbackData.substring(7);
                execute(myBotService.removeFromCart(chatId, itemId, message.getMessageId()));
            } else if (callbackData.equals("confirm_order")) {
                execute(myBotService.confirmOrder(chatId, message.getMessageId()));
            } else if (callbackData.startsWith("quantity_")) {
                String[] parts = callbackData.substring(9).split("_");
                String itemId = parts[0];
                int quantity = Integer.parseInt(parts[1]);
                execute(myBotService.updateQuantity(chatId, itemId, quantity, message.getMessageId()));
            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return "foodMoodRestaurant_bot";
    }

    @Override
    public String getBotToken() {
        return "8322912139:AAGMvsSEMZPldF0pb0vxAItVuZ95pyVocm4";
    }

    public void info(Long chatId, String firstname, String username, String message) {
        System.out.println(chatId + "   |   " + firstname + ": @" + username + "   |   " + message);
    }
}
