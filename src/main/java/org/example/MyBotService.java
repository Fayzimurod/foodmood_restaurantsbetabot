package org.example;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.Location;

import java.io.File;
import java.util.*;

public class MyBotService {

    private Map<Long, Map<String, Integer>> userCarts = new HashMap<>();
    private Map<String, MenuItem> menuItems = new HashMap<>();

    public MyBotService() {
        initializeMenu();
    }

    private void initializeMenu() {
        // Снеки
        menuItems.put("snack1", new MenuItem("snack1", "Картофель фри", "Снеки", 4.99, "images/snacks/fries.png"));
        menuItems.put("snack2", new MenuItem("snack2", "Луковые кольца", "Снеки", 5.99, "images/snacks/onion_rings.png"));
        menuItems.put("snack3", new MenuItem("snack3", "Куриные наггетсы", "Снеки", 6.99, "images/snacks/nuggets.png"));
        menuItems.put("snack4", new MenuItem("snack4", "Чесночный хлеб", "Снеки", 3.99, "images/snacks/garlic_bread.png"));
        menuItems.put("snack5", new MenuItem("snack5", "Моцарелла стикс", "Снеки", 7.99, "images/snacks/mozzarella_sticks.png"));
        menuItems.put("snack6", new MenuItem("snack6", "Куриные крылышки", "Снеки", 8.99, "images/snacks/wings.png"));

        // Десерты
        menuItems.put("dessert1", new MenuItem("dessert1", "Тирамису", "Десерты", 6.99, "images/desserts/tiramisu.png"));
        menuItems.put("dessert2", new MenuItem("dessert2", "Чизкейк", "Десерты", 5.99, "images/desserts/cheesecake.png"));
        menuItems.put("dessert3", new MenuItem("dessert3", "Шоколадный фондан", "Десерты", 7.99, "images/desserts/fondant.png"));
        menuItems.put("dessert4", new MenuItem("dessert4", "Мороженое", "Десерты", 4.99, "images/desserts/ice_cream.png"));
        menuItems.put("dessert5", new MenuItem("dessert5", "Панна котта", "Десерты", 5.49, "images/desserts/panna_cotta.png"));
        menuItems.put("dessert6", new MenuItem("dessert6", "Яблочный пирог", "Десерты", 4.99, "images/desserts/apple_pie.png"));
        menuItems.put("dessert7", new MenuItem("dessert7", "Крем брюле", "Десерты", 6.49, "images/desserts/creme_brulee.png"));
        menuItems.put("dessert8", new MenuItem("dessert8", "Торт Красный бархат", "Десерты", 7.99, "images/desserts/red_velvet.png"));

        // Коктейли
        menuItems.put("cocktail1", new MenuItem("cocktail1", "Мохито", "Коктейли", 8.99, "images/cocktails/mojito.png"));
        menuItems.put("cocktail2", new MenuItem("cocktail2", "Пина Колада", "Коктейли", 9.99, "images/cocktails/pina_colada.png"));
        menuItems.put("cocktail3", new MenuItem("cocktail3", "Маргарита", "Коктейли", 8.49, "images/cocktails/margarita.png"));
        menuItems.put("cocktail4", new MenuItem("cocktail4", "Космополитен", "Коктейли", 9.49, "images/cocktails/cosmopolitan.png"));
        menuItems.put("cocktail5", new MenuItem("cocktail5", "Кровавая Мэри", "Коктейли", 8.99, "images/cocktails/bloody_mary.png"));
        menuItems.put("cocktail6", new MenuItem("cocktail6", "Дайкири", "Коктейли", 9.49, "images/cocktails/daiquiri.png"));
        menuItems.put("cocktail7", new MenuItem("cocktail7", "Манхэттен", "Коктейли", 10.99, "images/cocktails/manhattan.png"));
        menuItems.put("cocktail8", new MenuItem("cocktail8", "Негрони", "Коктейли", 11.99, "images/cocktails/negroni.png"));

        // Блюда
        menuItems.put("dish1", new MenuItem("dish1", "Стейк Рибай", "Блюда", 24.99, "images/dishes/ribeye_steak.png"));
        menuItems.put("dish2", new MenuItem("dish2", "Лосось на гриле", "Блюда", 18.99, "images/dishes/grilled_salmon.png"));
        menuItems.put("dish3", new MenuItem("dish3", "Паста Карбонара", "Блюда", 14.99, "images/dishes/carbonara.png"));
        menuItems.put("dish4", new MenuItem("dish4", "Бургер с говядиной", "Блюда", 12.99, "images/dishes/beef_burger.png"));
        menuItems.put("dish5", new MenuItem("dish5", "Цыпленок по-французски", "Блюда", 16.99, "images/dishes/french_chicken.png"));
        menuItems.put("dish6", new MenuItem("dish6", "Пицца Маргарита", "Блюда", 13.99, "images/dishes/margherita_pizza.png"));
        menuItems.put("dish7", new MenuItem("dish7", "Салат Цезарь", "Блюда", 10.99, "images/dishes/caesar_salad.png"));
        menuItems.put("dish8", new MenuItem("dish8", "Том Ям", "Блюда", 15.99, "images/dishes/tom_yum.png"));
        menuItems.put("dish9", new MenuItem("dish9", "Бефстроганов", "Блюда", 17.99, "images/dishes/beef_stroganoff.png"));
        menuItems.put("dish10", new MenuItem("dish10", "Ризотто с грибами", "Блюда", 13.99, "images/dishes/mushroom_risotto.png"));

        // Напитки
        menuItems.put("drink1", new MenuItem("drink1", "Кола", "Напитки", 2.99, "images/drinks/cola.png"));
        menuItems.put("drink2", new MenuItem("drink2", "Фанта", "Напитки", 2.99, "images/drinks/fanta.png"));
        menuItems.put("drink3", new MenuItem("drink3", "Спрайт", "Напитки", 2.99, "images/drinks/sprite.png"));
        menuItems.put("drink4", new MenuItem("drink4", "Вода", "Напитки", 1.99, "images/drinks/water.png"));
        menuItems.put("drink5", new MenuItem("drink5", "Сок апельсиновый", "Напитки", 3.49, "images/drinks/orange_juice.png"));
        menuItems.put("drink6", new MenuItem("drink6", "Лимонад", "Напитки", 3.99, "images/drinks/lemonade.png"));

        // Чай
        menuItems.put("tea1", new MenuItem("tea1", "Черный чай", "Чай", 2.49, "images/tea/black_tea.png"));
        menuItems.put("tea2", new MenuItem("tea2", "Зеленый чай", "Чай", 2.49, "images/tea/green_tea.png"));
        menuItems.put("tea3", new MenuItem("tea3", "Чай с лимоном", "Чай", 2.99, "images/tea/lemon_tea.png"));
        menuItems.put("tea4", new MenuItem("tea4", "Бардакчай", "Чай", 3.49, "images/tea/bardakchai.png"));
        menuItems.put("tea5", new MenuItem("tea5", "Каркаде", "Чай", 2.99, "images/tea/hibiscus_tea.png"));
    }

    public SendMessage sendWelcomeMessage(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("🍽️ *Добро пожаловать в FoodMood Restaurant!* 🍽️\n\n" +
                "Мы рады приветствовать вас! Выберите опцию из меню ниже:");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> rowList = new ArrayList<>();

        // Row 1
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("🛍️ Order"));
        row1.add(new KeyboardButton("📨 Vacancies"));

        // Row 2
        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("📍 Location"));
        row2.add(new KeyboardButton("📋 View Cart"));

        rowList.add(row1);
        rowList.add(row2);

        replyKeyboardMarkup.setKeyboard(rowList);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setParseMode("Markdown");

        return sendMessage;
    }

    public SendMessage showCategories(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("🏷️ *Выберите категорию:*");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        // Row 1
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(createInlineButton("🍟 Снеки", "category_snacks"));
        row1.add(createInlineButton("🍰 Десерты", "category_desserts"));

        // Row 2
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(createInlineButton("🍹 Коктейли", "category_cocktails"));
        row2.add(createInlineButton("🍽️ Блюда", "category_dishes"));

        // Row 3
        List<InlineKeyboardButton> row3 = new ArrayList<>();
        row3.add(createInlineButton("🥤 Напитки", "category_drinks"));
        row3.add(createInlineButton("🍵 Чай", "category_tea"));

        // Row 4 - Back to main menu
        List<InlineKeyboardButton> row4 = new ArrayList<>();
        row4.add(createInlineButton("🔙 Главное меню", "cart_mainmenu"));

        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        rows.add(row4);

        inlineKeyboardMarkup.setKeyboard(rows);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        sendMessage.setParseMode("Markdown");

        return sendMessage;
    }

    public SendMessage showCategoryItems(long chatId, String category) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        String categoryName = getCategoryName(category);
        sendMessage.setText("🍽️ *" + categoryName + "*\n\nВыберите блюдо:");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        // Filter items by category and create buttons
        menuItems.entrySet().stream()
                .filter(entry -> entry.getValue().getCategory().equals(categoryName))
                .forEach(entry -> {
                    MenuItem item = entry.getValue();
                    List<InlineKeyboardButton> row = new ArrayList<>();

                    InlineKeyboardButton button = new InlineKeyboardButton();
                    button.setText(item.getName() + " - $" + item.getPrice());
                    button.setCallbackData("quantity_" + item.getId() + "_1");
                    row.add(button);

                    rows.add(row);
                });

        // Navigation buttons
        List<InlineKeyboardButton> navRow1 = new ArrayList<>();
        navRow1.add(createInlineButton("📋 Корзина", "cart_view"));
        navRow1.add(createInlineButton("🏷️ Категории", "cart_categories"));

        List<InlineKeyboardButton> navRow2 = new ArrayList<>();
        navRow2.add(createInlineButton("🔙 Главное меню", "cart_mainmenu"));

        rows.add(navRow1);
        rows.add(navRow2);

        inlineKeyboardMarkup.setKeyboard(rows);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        sendMessage.setParseMode("Markdown");

        return sendMessage;
    }

    public EditMessageText updateQuantity(long chatId, String itemId, int quantity, int messageId) {
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId);
        editMessage.setMessageId(messageId);

        MenuItem item = menuItems.get(itemId);

        if (quantity <= 0) {
            editMessage.setText("❌ Количество не может быть меньше 1");
            return editMessage;
        }

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        // Item info
        List<InlineKeyboardButton> infoRow = new ArrayList<>();
        InlineKeyboardButton infoButton = new InlineKeyboardButton();
        infoButton.setText(item.getName() + " - $" + item.getPrice() + " x" + quantity);
        infoButton.setCallbackData("info_" + itemId);
        infoRow.add(infoButton);
        rows.add(infoRow);

        // Quantity controls
        List<InlineKeyboardButton> quantityRow = new ArrayList<>();
        quantityRow.add(createInlineButton("➖", "quantity_" + itemId + "_" + (quantity - 1)));
        quantityRow.add(createInlineButton("" + quantity, "quantity_" + itemId + "_" + quantity));
        quantityRow.add(createInlineButton("➕", "quantity_" + itemId + "_" + (quantity + 1)));
        rows.add(quantityRow);

        // Action buttons
        List<InlineKeyboardButton> actionRow = new ArrayList<>();
        actionRow.add(createInlineButton("✅ Добавить в корзину", "add_" + itemId + "_" + quantity));
        actionRow.add(createInlineButton("❌ Отмена", "category_" + getCategoryKey(item.getCategory())));
        rows.add(actionRow);

        // Navigation
        List<InlineKeyboardButton> navRow = new ArrayList<>();
        navRow.add(createInlineButton("🔙 Назад", "category_" + getCategoryKey(item.getCategory())));
        rows.add(navRow);

        editMessage.setText("🎯 *Настройте количество:*\n\n" + item.getName() + "\nЦена: $" + item.getPrice());
        inlineKeyboardMarkup.setKeyboard(rows);
        editMessage.setReplyMarkup(inlineKeyboardMarkup);
        editMessage.setParseMode("Markdown");

        return editMessage;
    }

    public EditMessageText addToCart(long chatId, String itemId, int quantity, int messageId) {
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId);
        editMessage.setMessageId(messageId);

        // Initialize cart if not exists
        userCarts.putIfAbsent(chatId, new HashMap<>());
        Map<String, Integer> cart = userCarts.get(chatId);

        // Add item to cart
        cart.put(itemId, cart.getOrDefault(itemId, 0) + quantity);

        MenuItem item = menuItems.get(itemId);
        editMessage.setText("✅ *Добавлено в корзину!*\n\n" +
                item.getName() + " x" + quantity + "\n" +
                "Цена: $" + String.format("%.2f", item.getPrice() * quantity) + "\n\n" +
                "Продолжайте выбирать блюда!");

        // Return to categories
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(createInlineButton("🏷️ Продолжить заказ", "cart_categories"));
        row1.add(createInlineButton("📋 Корзина", "cart_view"));

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(createInlineButton("🔙 Главное меню", "cart_mainmenu"));

        rows.add(row1);
        rows.add(row2);

        inlineKeyboardMarkup.setKeyboard(rows);
        editMessage.setReplyMarkup(inlineKeyboardMarkup);
        editMessage.setParseMode("Markdown");

        return editMessage;
    }

    public SendMessage viewCart(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        Map<String, Integer> cart = userCarts.get(chatId);
        if (cart == null || cart.isEmpty()) {
            sendMessage.setText("🛒 *Ваша корзина пуста*\n\nВыберите блюда из меню 🍽️");
        } else {
            StringBuilder cartText = new StringBuilder("🛒 *Ваш заказ:*\n\n");
            double total = 0;

            for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                MenuItem item = menuItems.get(entry.getKey());
                double itemTotal = item.getPrice() * entry.getValue();
                total += itemTotal;
                cartText.append("• ").append(item.getName())
                        .append(" x").append(entry.getValue())
                        .append(" - $").append(String.format("%.2f", itemTotal))
                        .append("\n");
            }

            cartText.append("\n💵 *Итого: $").append(String.format("%.2f", total)).append("*");

            sendMessage.setText(cartText.toString());

            // Add inline buttons for cart management
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rows = new ArrayList<>();

            List<InlineKeyboardButton> row1 = new ArrayList<>();
            row1.add(createInlineButton("✅ Подтвердить заказ", "confirm_order"));
            row1.add(createInlineButton("🗑️ Очистить корзину", "cart_clear"));

            List<InlineKeyboardButton> row2 = new ArrayList<>();
            row2.add(createInlineButton("🏷️ Продолжить заказ", "cart_categories"));
            row2.add(createInlineButton("🔙 Главное меню", "cart_mainmenu"));

            rows.add(row1);
            rows.add(row2);

            inlineKeyboardMarkup.setKeyboard(rows);
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        }

        sendMessage.setParseMode("Markdown");
        return sendMessage;
    }

    public EditMessageText handleCartAction(long chatId, String action, int messageId) {
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId);
        editMessage.setMessageId(messageId);

        switch (action) {
            case "view":
                Map<String, Integer> cart = userCarts.get(chatId);
                if (cart == null || cart.isEmpty()) {
                    editMessage.setText("🛒 *Ваша корзина пуста*\n\nВыберите блюда из меню 🍽️");
                } else {
                    StringBuilder cartText = new StringBuilder("🛒 *Ваш заказ:*\n\n");
                    double total = 0;

                    for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                        MenuItem item = menuItems.get(entry.getKey());
                        double itemTotal = item.getPrice() * entry.getValue();
                        total += itemTotal;
                        cartText.append("• ").append(item.getName())
                                .append(" x").append(entry.getValue())
                                .append(" - $").append(String.format("%.2f", itemTotal))
                                .append("\n");
                    }

                    cartText.append("\n💵 *Итого: $").append(String.format("%.2f", total)).append("*");
                    editMessage.setText(cartText.toString());

                    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                    List<List<InlineKeyboardButton>> rows = new ArrayList<>();

                    List<InlineKeyboardButton> row1 = new ArrayList<>();
                    row1.add(createInlineButton("✅ Подтвердить заказ", "confirm_order"));
                    row1.add(createInlineButton("🗑️ Очистить корзину", "cart_clear"));

                    List<InlineKeyboardButton> row2 = new ArrayList<>();
                    row2.add(createInlineButton("🏷️ Продолжить заказ", "cart_categories"));
                    row2.add(createInlineButton("🔙 Главное меню", "cart_mainmenu"));

                    rows.add(row1);
                    rows.add(row2);

                    inlineKeyboardMarkup.setKeyboard(rows);
                    editMessage.setReplyMarkup(inlineKeyboardMarkup);
                }
                break;
            case "clear":
                userCarts.remove(chatId);
                editMessage.setText("🗑️ *Корзина очищена*\n\nТеперь вы можете начать новый заказ 🍽️");
                break;
            case "categories":
                editMessage.setText("🏷️ *Выберите категорию:*");

                InlineKeyboardMarkup categoriesKeyboard = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> categoriesRows = new ArrayList<>();

                List<InlineKeyboardButton> row1 = new ArrayList<>();
                row1.add(createInlineButton("🍟 Снеки", "category_snacks"));
                row1.add(createInlineButton("🍰 Десерты", "category_desserts"));

                List<InlineKeyboardButton> row2 = new ArrayList<>();
                row2.add(createInlineButton("🍹 Коктейли", "category_cocktails"));
                row2.add(createInlineButton("🍽️ Блюда", "category_dishes"));

                List<InlineKeyboardButton> row3 = new ArrayList<>();
                row3.add(createInlineButton("🥤 Напитки", "category_drinks"));
                row3.add(createInlineButton("🍵 Чай", "category_tea"));

                List<InlineKeyboardButton> row4 = new ArrayList<>();
                row4.add(createInlineButton("🔙 Главное меню", "cart_mainmenu"));

                categoriesRows.add(row1);
                categoriesRows.add(row2);
                categoriesRows.add(row3);
                categoriesRows.add(row4);

                categoriesKeyboard.setKeyboard(categoriesRows);
                editMessage.setReplyMarkup(categoriesKeyboard);
                break;
            case "mainmenu":
                editMessage.setText("🍽️ *Добро пожаловать в FoodMood Restaurant!* 🍽️\n\n" +
                        "Мы рады приветствовать вас! Выберите опцию из меню ниже:");
                break;
        }

        editMessage.setParseMode("Markdown");
        return editMessage;
    }

    public EditMessageText removeFromCart(long chatId, String itemId, int messageId) {
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId);
        editMessage.setMessageId(messageId);

        Map<String, Integer> cart = userCarts.get(chatId);
        if (cart != null && cart.containsKey(itemId)) {
            MenuItem item = menuItems.get(itemId);
            cart.remove(itemId);
            editMessage.setText("🗑️ *Удалено из корзины:* " + item.getName());
        } else {
            editMessage.setText("❌ Товар не найден в корзине");
        }

        editMessage.setParseMode("Markdown");
        return editMessage;
    }

    public EditMessageText confirmOrder(long chatId, int messageId) {
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId);
        editMessage.setMessageId(messageId);

        Map<String, Integer> cart = userCarts.get(chatId);
        if (cart == null || cart.isEmpty()) {
            editMessage.setText("🛒 *Ваша корзина пуста*");
        } else {
            StringBuilder orderText = new StringBuilder("✅ *Заказ подтвержден!*\n\n");
            orderText.append("📋 *Ваш заказ:*\n");
            double total = 0;

            for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                MenuItem item = menuItems.get(entry.getKey());
                double itemTotal = item.getPrice() * entry.getValue();
                total += itemTotal;
                orderText.append("• ").append(item.getName())
                        .append(" x").append(entry.getValue())
                        .append(" - $").append(String.format("%.2f", itemTotal))
                        .append("\n");
            }

            orderText.append("\n💵 *Общая сумма: $").append(String.format("%.2f", total)).append("*\n\n");
            orderText.append("🕐 Время приготовления: 20-30 минут\n");
            orderText.append("📍 Адрес: ул. Ресторанная, 123\n");
            orderText.append("📞 Контакт: +998-(94)-060-00-00\n\n");
            orderText.append("Способ оплаты в Пункте Выдачи.\n\n");
            orderText.append("Спасибо за заказ! 🎉");

            editMessage.setText(orderText.toString());

            // Clear the cart after order confirmation
            userCarts.remove(chatId);
        }

        editMessage.setParseMode("Markdown");
        return editMessage;
    }

    public SendMessage showVacancies(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("📨 *Вакансии в FoodMood Restaurant*\n\n" +
                "Мы всегда рады новым талантам! 🎉\n\n" +
                "*Открытые позиции:*\n" +
                "• 🍳 Шеф-повар\n" +
                "• 👨‍🍳 Повар\n" +
                "• 🍹 Бармен\n" +
                "• 👨‍💼 Официант\n" +
                "• 🧹 Уборщик\n\n" +
                "📞 Для получения подробной информации звоните: +998-(94)-060-00-00\n" +
                "📧 Или отправляйте резюме на: hr@foodmood.com");

        sendMessage.setParseMode("Markdown");
        return sendMessage;
    }

    public SendLocation sendLocation(long chatId) {
        SendLocation sendLocation = new SendLocation();
        sendLocation.setChatId(chatId);
        sendLocation.setLatitude(40.7128);
        sendLocation.setLongitude(-74.0060);

        return sendLocation;
    }

    public SendPhoto sendPhoto(long chatId) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setCaption("🎨 This is the painting");
        sendPhoto.setPhoto(new InputFile(new File("src/Images/picture.png")));
        return sendPhoto;
    }

    public SendMessage menu(long chatId) {
        return sendWelcomeMessage(chatId);
    }

    private InlineKeyboardButton createInlineButton(String text, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callbackData);
        return button;
    }

    private String getCategoryName(String category) {
        switch (category) {
            case "snacks": return "Снеки";
            case "desserts": return "Десерты";
            case "cocktails": return "Коктейли";
            case "dishes": return "Блюда";
            case "drinks": return "Напитки";
            case "tea": return "Чай";
            default: return category;
        }
    }

    private String getCategoryKey(String categoryName) {
        switch (categoryName) {
            case "Снеки": return "snacks";
            case "Десерты": return "desserts";
            case "Коктейли": return "cocktails";
            case "Блюда": return "dishes";
            case "Напитки": return "drinks";
            case "Чай": return "tea";
            default: return categoryName.toLowerCase();
        }
    }

    // Inner class for menu items
    private static class MenuItem {
        private String id;
        private String name;
        private String category;
        private double price;
        private String imagePath;

        public MenuItem(String id, String name, String category, double price, String imagePath) {
            this.id = id;
            this.name = name;
            this.category = category;
            this.price = price;
            this.imagePath = imagePath;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getCategory() {
            return category;
        }

        public double getPrice() {
            return price;
        }

        public String getImagePath() {
            return imagePath;
        }
    }
}
