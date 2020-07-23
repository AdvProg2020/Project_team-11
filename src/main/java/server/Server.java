package server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.*;
import model.*;

import java.io.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.util.*;

public class Server {
    private static final int storeServerPort = 8888;
    private static final int bankServerPort = 9999;
    private static ArrayList<String> onlineSupports = new ArrayList<>();

    public static int getBankServerPort() {
        return bankServerPort;
    }

    static class AppHandler extends Thread {
        private DataOutputStream dataOutputStream;
        private DataInputStream dataInputStream;
        private Account currentAccount;
        private DataOutputStream bankDataOutputStream;
        private DataInputStream bankDataInputStream;
        private String bankToken;

        public AppHandler(DataOutputStream dataOutputStream, DataInputStream dataInputStream) throws IOException {
            this.dataOutputStream = dataOutputStream;
            this.dataInputStream = dataInputStream;
            this.currentAccount = null;
            this.bankToken = "";
            Socket socket = new Socket("127.0.0.1", bankServerPort);
            this.bankDataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            this.bankDataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            System.out.println(bankDataInputStream.readUTF());
        }

        @Override
        public void run() {
            outerLoop :
            while (true) {
                try {
                    Type foundListType;
                    Gson gson = new Gson();
                    String input;
                    do {
                        input = dataInputStream.readUTF();
                    } while (input.isEmpty());
                    System.out.println(input);
                    switch (input) {
                        case "has admin":
                            dataOutputStream.writeBoolean(DataBase.getDataBase().getHasAdminAccountCreated());
                            dataOutputStream.flush();
                            break;
                        case "register":
                            String data = dataInputStream.readUTF();
                            foundListType = new TypeToken<ArrayList<String>>() {}.getType();
                            ArrayList<String> info = gson.fromJson(data, foundListType);
                            AllAccountZone.createAccount(info);
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "get products":
                            BuyerZone.setAuctionPrice();
                            dataOutputStream.writeUTF(gson.toJson(DataBase.getDataBase().getAllProducts()));
                            dataOutputStream.flush();
                            break;
                        case "check username":
                            String username = dataInputStream.readUTF();
                            dataOutputStream.writeBoolean(AllAccountZone.isUsernameValid(username));
                            dataOutputStream.flush();
                            break;
                        case "sign in":
                            data = dataInputStream.readUTF();
                            foundListType = new TypeToken<ArrayList<String>>() {}.getType();
                            info = gson.fromJson(data, foundListType);
                            String result = AllAccountZone.loginUser(info);
                            if (result.startsWith("Login successfully")) {
                                currentAccount = AllAccountZone.getAccountByUsername(info.get(1));
                                if (currentAccount instanceof Support)
                                    onlineSupports.add(currentAccount.getUsername());
                            }
                            dataOutputStream.writeUTF(result);
                            dataOutputStream.flush();
                            break;
                        case "logout":
                            if (currentAccount instanceof Support)
                                onlineSupports.remove(currentAccount.getUsername());
                            currentAccount = null;
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "edit personal info":
                            String field = dataInputStream.readUTF();
                            String newValue = dataInputStream.readUTF();
                            AllAccountZone.editPersonalInfo(field, newValue, currentAccount);
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "check discount code":
                            String code = dataInputStream.readUTF();
                            dataOutputStream.writeBoolean(AdminZone.getDiscountByCode(code) != null);
                            dataOutputStream.flush();
                            break;
                        case "remove discount":
                            code = dataInputStream.readUTF();
                            AdminZone.removeDiscount(code);
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "edit discount":
                            field = dataInputStream.readUTF();
                            newValue = dataInputStream.readUTF();
                            code = dataInputStream.readUTF();
                            AdminZone.editDiscount(field, newValue, code);
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "check category name":
                            String name = dataInputStream.readUTF();
                            dataOutputStream.writeBoolean(AllAccountZone.getCategoryByName(name) == null);
                            dataOutputStream.flush();
                            break;
                        case "edit category":
                            field = dataInputStream.readUTF();
                            newValue = dataInputStream.readUTF();
                            name = dataInputStream.readUTF();
                            String lastField = dataInputStream.readUTF();
                            AdminZone.editCategory(field, newValue, name, lastField);
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "edit product":
                            String requestDescription = dataInputStream.readUTF();
                            SellerZone.sendEditProductRequest(requestDescription, currentAccount);
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "add product":
                            data = dataInputStream.readUTF();
                            foundListType = new TypeToken<ArrayList<String>>() {}.getType();
                            info = gson.fromJson(data, foundListType);
                            data = dataInputStream.readUTF();
                            foundListType = new TypeToken<HashMap<String, String>>() {}.getType();
                            HashMap<String, String> features = gson.fromJson(data, foundListType);
                            int productId = SellerZone.sendAddNewProductRequest(info, features, currentAccount);
                            dataOutputStream.writeUTF(String.valueOf(productId));
                            dataOutputStream.flush();
                            break;
                        case "add auction":
                            data = dataInputStream.readUTF();
                            foundListType = new TypeToken<ArrayList<String>>() {}.getType();
                            info = gson.fromJson(data, foundListType);
                            SellerZone.createAuction(info, currentAccount);
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "edit auction":
                            field = dataInputStream.readUTF();
                            newValue = dataInputStream.readUTF();
                            int auctionId = Integer.parseInt(dataInputStream.readUTF());
                            SellerZone.sendEditAuctionRequest(field, newValue, auctionId, currentAccount);
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "check product id":
                            productId = Integer.parseInt(dataInputStream.readUTF());
                            dataOutputStream.writeBoolean(SellerZone.getProductById(productId) != null);
                            dataOutputStream.flush();
                            break;
                        case "can compare product":
                            int productId1 = Integer.parseInt(dataInputStream.readUTF());
                            int productId2 = Integer.parseInt(dataInputStream.readUTF());
                            dataOutputStream.writeUTF(AllAccountZone.compareTwoProduct(productId1, productId2));
                            dataOutputStream.flush();
                            break;
                        case "get categories":
                            dataOutputStream.writeUTF(gson.toJson(AllAccountZone.getCategories()));
                            dataOutputStream.flush();
                            break;
                        case "get category feature":
                            name = dataInputStream.readUTF();
                            dataOutputStream.writeUTF(gson.toJson(AdminZone.getCategoryFeature(name)));
                            dataOutputStream.flush();
                            break;
                        case "get product":
                            productId = dataInputStream.readInt();
                            BuyerZone.setAuctionPrice();
                            dataOutputStream.writeUTF(gson.toJson(SellerZone.getProductById(productId)));
                            dataOutputStream.flush();
                            break;
                        case "is buyer":
                            dataOutputStream.writeBoolean(AllAccountZone.canUserBuyOrComment(currentAccount));
                            dataOutputStream.flush();
                            break;
                        case "has bought":
                            productId = dataInputStream.readInt();
                            dataOutputStream.writeBoolean(BuyerZone.hasUserBoughtProduct(productId, currentAccount));
                            dataOutputStream.flush();
                            break;
                        case "rate":
                            productId = dataInputStream.readInt();
                            int score = Integer.parseInt(dataInputStream.readUTF());
                            BuyerZone.createRate(productId, score, currentAccount);
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "compare product":
                            productId1 = dataInputStream.readInt();
                            productId2 = dataInputStream.readInt();
                            dataOutputStream.writeUTF(AllAccountZone.compareTwoProduct(productId1, productId2));
                            dataOutputStream.flush();
                            break;
                        case "comment":
                            String comment = dataInputStream.readUTF();
                            productId = dataInputStream.readInt();
                            AllAccountZone.createComment(comment, productId, currentAccount);
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "add to cart":
                            productId = dataInputStream.readInt();
                            AllAccountZone.addProductToCart(productId, currentAccount);
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "get personal info":
                            dataOutputStream.writeUTF(gson.toJson(AllAccountZone.getPersonalInfo(currentAccount)));
                            dataOutputStream.flush();
                            break;
                        case "get users":
                            dataOutputStream.writeUTF(gson.toJson(AdminZone.getUsers()));
                            dataOutputStream.flush();
                            break;
                        case "delete user":
                            username = dataInputStream.readUTF();
                            AdminZone.deleteUser(username);
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "get discounts":
                            dataOutputStream.writeUTF(gson.toJson(AdminZone.getDiscountCodes()));
                            dataOutputStream.flush();
                            break;
                        case "get discount info":
                            code = dataInputStream.readUTF();
                            dataOutputStream.writeUTF(gson.toJson(AdminZone.getDiscountInfo(code)));
                            dataOutputStream.flush();
                            break;
                        case "get buyer":
                            username = dataInputStream.readUTF();
                            dataOutputStream.writeBoolean(AdminZone.getBuyerByUsername(username) != null);
                            dataOutputStream.flush();
                            break;
                        case "create discount":
                            data = dataInputStream.readUTF();
                            foundListType = new TypeToken<ArrayList<String>>() {}.getType();
                            ArrayList<String> discountInfo = gson.fromJson(data, foundListType);
                            data = dataInputStream.readUTF();
                            foundListType = new TypeToken<ArrayList<String>>() {}.getType();
                            ArrayList<String> allowedUsers = gson.fromJson(data, foundListType);
                            AdminZone.createDiscount(discountInfo, allowedUsers);
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "get table products":
                            dataOutputStream.writeUTF(gson.toJson(AdminZone.getAllProducts()));
                            dataOutputStream.flush();
                            break;
                        case "get requests":
                            dataOutputStream.writeUTF(gson.toJson(AdminZone.getAllRequests()));
                            dataOutputStream.flush();
                            break;
                        case "remove request":
                            int requestId = Integer.parseInt(dataInputStream.readUTF());
                            AdminZone.removeRequest(requestId);
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "accept request":
                            requestId = Integer.parseInt(dataInputStream.readUTF());
                            AdminZone.acceptRequest(requestId);
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "decline request":
                            requestId = Integer.parseInt(dataInputStream.readUTF());
                            AdminZone.declineRequest(requestId);
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "remove category":
                            name = dataInputStream.readUTF();
                            AdminZone.removeCategory(name);
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "create category":
                            name = dataInputStream.readUTF();
                            data = dataInputStream.readUTF();
                            foundListType = new TypeToken<ArrayList<String>>() {}.getType();
                            ArrayList<String> feature = gson.fromJson(data, foundListType);
                            AdminZone.createCategory(name, feature);
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "price with auction":
                            String price = String.valueOf(BuyerZone.calculatePriceWithAuctions(currentAccount));
                            System.out.println(price);
                            dataOutputStream.writeUTF(price);
                            dataOutputStream.flush();
                            break;
                        case "check discount":
                            code = dataInputStream.readUTF();
                            dataOutputStream.writeUTF(BuyerZone.checkDiscountCode(code, currentAccount));
                            dataOutputStream.flush();
                            break;
                        case "product in cart":
                            dataOutputStream.writeUTF(gson.toJson(BuyerZone.getProductsInCart(currentAccount)));
                            dataOutputStream.flush();
                            break;
                        case "get product name":
                            productId = dataInputStream.readInt();
                            dataOutputStream.writeUTF(SellerZone.getProductById(productId).getGeneralFeature().getName());
                            dataOutputStream.flush();
                            break;
                        case "get auction price":
                            productId = dataInputStream.readInt();
                            dataOutputStream.writeUTF(String.valueOf(SellerZone.getProductById(productId)
                                    .getGeneralFeature().getAuctionPrice()));
                            dataOutputStream.flush();
                            break;
                        case "change number":
                            productId = dataInputStream.readInt();
                            int amount = dataInputStream.readInt();
                            dataOutputStream.writeBoolean(BuyerZone
                                    .changeNumberOFProductInCart(productId, amount, currentAccount));
                            dataOutputStream.flush();
                            break;
                        case "remove product in cart":
                            BuyerZone.removeProductFromCart(currentAccount);
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "buyer price":
                            dataOutputStream.writeLong(BuyerZone.calculatePriceWithDiscountsAndAuctions(currentAccount));
                            dataOutputStream.flush();
                            break;
                        case "can pay":
                            dataOutputStream.writeBoolean(BuyerZone.canPayMoney(currentAccount));
                            dataOutputStream.flush();
                            break;
                        case "pay":
                            String type = dataInputStream.readUTF();
                            String address = dataInputStream.readUTF();
                            String number = dataInputStream.readUTF();
                            BuyerZone.payMoney(currentAccount, type, bankDataOutputStream, bankDataInputStream, address, number);
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "get orders":
                            dataOutputStream.writeUTF(gson.toJson(BuyerZone.getOrdersInfo(currentAccount)));
                            dataOutputStream.flush();
                            break;
                        case "get all buy logs":
                            dataOutputStream.writeUTF(gson.toJson(AdminZone.getAllOrders()));
                            dataOutputStream.flush();
                            break;
                        case "get buy log address":
                            dataOutputStream.writeUTF(gson.toJson(AdminZone.getOrderAddress()));
                            dataOutputStream.flush();
                            break;
                        case "send buy log":
                            int logId = Integer.parseInt(dataInputStream.readUTF());
                            AdminZone.sendBuyLog(logId);
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "get buyer discounts":
                            dataOutputStream.writeUTF(gson.toJson(BuyerZone.getBuyerDiscounts(currentAccount)));
                            dataOutputStream.flush();
                            break;
                        case "get sales history":
                            dataOutputStream.writeUTF(gson.toJson(SellerZone.getSellerHistory(currentAccount)));
                            dataOutputStream.flush();
                            break;
                        case "get seller products":
                            dataOutputStream.writeUTF(gson.toJson(SellerZone.getSellerProducts(currentAccount)));
                            dataOutputStream.flush();
                            break;
                        case "remove seller product":
                            //TODO : request
                            productId = dataInputStream.readInt();
                            SellerZone.removeProduct(productId);
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "product details":
                            productId = Integer.parseInt(dataInputStream.readUTF());
                            dataOutputStream.writeUTF(gson.toJson(SellerZone.getSellerProductDetails(productId)));
                            dataOutputStream.flush();
                            break;
                        case "get seller auctions":
                            dataOutputStream.writeUTF(gson.toJson(SellerZone.getSellerAuctions(currentAccount)));
                            dataOutputStream.flush();
                            break;
                        case "get auction details":
                            auctionId = Integer.parseInt(dataInputStream.readUTF());
                            dataOutputStream.writeUTF(gson.toJson(SellerZone.getAuctionDetail(auctionId, currentAccount)));
                            dataOutputStream.flush();
                            break;
                        case "remove auction product":
                            auctionId = Integer.parseInt(dataInputStream.readUTF());
                            productId = Integer.parseInt(dataInputStream.readUTF());
                            SellerZone.removeProductFromAuction(auctionId, productId, currentAccount);
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "has auction":
                            productId = Integer.parseInt(dataInputStream.readUTF());
                            dataOutputStream.writeBoolean(SellerZone.hasProductAuction(productId));
                            dataOutputStream.flush();
                            break;
                        case "seller has product":
                            productId = Integer.parseInt(dataInputStream.readUTF());
                            dataOutputStream.writeBoolean(SellerZone.hasProduct(productId, currentAccount));
                            dataOutputStream.flush();
                            break;
                        case "add auction product":
                            auctionId = Integer.parseInt(dataInputStream.readUTF());
                            productId = Integer.parseInt(dataInputStream.readUTF());
                            SellerZone.addProductToAuction(auctionId, productId, currentAccount);
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "remove product":
                            productId = dataInputStream.readInt();
                            SellerZone.removeProduct(productId);
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "set bank operation":
                            int commission = Integer.parseInt(dataInputStream.readUTF());
                            long minMoney = Long.parseLong(dataInputStream.readUTF());
                            String password = dataInputStream.readUTF();
                            new BankOperation(commission, minMoney, password);
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "get bank operations":
                            dataOutputStream.writeUTF(gson.toJson(DataBase.getDataBase().getBankOperation()));
                            dataOutputStream.flush();
                            break;
                        case "edit bank operation":
                            field = dataInputStream.readUTF();
                            newValue = dataInputStream.readUTF();
                            AdminZone.editBankOperation(field, newValue);
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "create charge receipt":
                            long money = Long.parseLong(dataInputStream.readUTF());
                            if (bankToken.isEmpty()) {
                                bankDataOutputStream.writeUTF("get_token " + currentAccount.getUsername() + " " +
                                        currentAccount.getPassword());
                                bankDataOutputStream.flush();
                                bankToken = bankDataInputStream.readUTF().trim();
                            }
                            bankDataOutputStream.writeUTF("create_receipt " + bankToken + " move " +
                                    money + " " + ((Buyer) currentAccount).getBankAccountId() + " " +
                                    DataBase.getDataBase().getBankOperation().getAccountId());
                            bankDataOutputStream.flush();
                            result = bankDataInputStream.readUTF();
                            if (result.equals("token expired")) {
                                bankDataOutputStream.writeUTF("get_token " + currentAccount.getUsername() + " " +
                                        currentAccount.getPassword());
                                bankDataOutputStream.flush();
                                bankToken = bankDataInputStream.readUTF().trim();
                                bankDataOutputStream.writeUTF("create_receipt " + bankToken + " move " +
                                        money + " " + ((Buyer) currentAccount).getBankAccountId() + " " +
                                        DataBase.getDataBase().getBankOperation().getAccountId());
                                bankDataOutputStream.flush();
                                result = bankDataInputStream.readUTF();
                            }
                            dataOutputStream.writeUTF(result);
                            dataOutputStream.flush();
                            break;
                        case "pay receipt":
                            int receiptId = dataInputStream.readInt();
                            bankDataOutputStream.writeUTF("pay " + receiptId);
                            bankDataOutputStream.flush();
                            dataOutputStream.writeUTF(bankDataInputStream.readUTF());
                            dataOutputStream.flush();
                            break;
                        case "increase money":
                            money = Long.parseLong(dataInputStream.readUTF());
                            if (currentAccount instanceof Buyer) {
                                ((Buyer) currentAccount).setWallet(((Buyer) currentAccount).getWallet() + money);
                            } else {
                                ((Seller) currentAccount).setWallet(((Seller) currentAccount).getWallet() + money);
                            }
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "create withdraw receipt":
                            money = dataInputStream.readLong();
                            long accountId = dataInputStream.readLong();
                            if (bankToken.isEmpty()) {
                                bankDataOutputStream.writeUTF("get_token admin " +
                                        DataBase.getDataBase().getBankOperation().getPassword());
                                bankDataOutputStream.flush();
                                bankToken = bankDataInputStream.readUTF().trim();
                            }
                            bankDataOutputStream.writeUTF("create_receipt " + bankToken + " move " + money +
                                    " " + DataBase.getDataBase().getBankOperation().getAccountId() + " " + accountId);
                            bankDataOutputStream.flush();
                            result = bankDataInputStream.readUTF();
                            if (result.equals("token expired")) {
                                bankDataOutputStream.writeUTF("get_token admin " +
                                        DataBase.getDataBase().getBankOperation().getPassword());
                                bankDataOutputStream.flush();
                                bankToken = bankDataInputStream.readUTF().trim();
                                bankDataOutputStream.writeUTF("create_receipt " + bankToken + " move " + money +
                                        " " + DataBase.getDataBase().getBankOperation().getAccountId() + " " + accountId);
                                bankDataOutputStream.flush();
                                result = bankDataInputStream.readUTF();
                            }
                            dataOutputStream.writeUTF(result);
                            dataOutputStream.flush();
                            break;
                        case "decrease money":
                            money = Long.parseLong(dataInputStream.readUTF());
                            accountId = Long.parseLong(dataInputStream.readUTF());
                            Account account = AllAccountZone.getAccountByBankId(accountId);
                            if (account instanceof Buyer) {
                                ((Buyer) account).setWallet(((Buyer) account).getWallet() - money);
                            } else {
                                ((Seller) account).setWallet(((Seller) account).getWallet() - money);
                            }
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "get bank account id":
                            dataOutputStream.writeLong(((Seller)currentAccount).getBankAccountId());
                            dataOutputStream.flush();
                            break;
                        case "request withdraw money":
                            money = Long.parseLong(dataInputStream.readUTF());
                            accountId =  dataInputStream.readLong();
                            DataBase.getDataBase().getBankOperation().addSellerWithdrawRequest(money, accountId);
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "get min money":
                            dataOutputStream.writeLong(DataBase.getDataBase().getBankOperation().getMinimumMoney());
                            dataOutputStream.flush();
                            break;
                        case "get message senders":
                            dataOutputStream.writeUTF(gson.toJson(((Support) currentAccount).getMessages().keySet()));
                            dataOutputStream.flush();
                            break;
                        case "get messages":
                            String sender = dataInputStream.readUTF();
                            dataOutputStream.writeUTF(gson.toJson(((Support) currentAccount).getMessages().get(sender)));
                            dataOutputStream.flush();
                            break;
                        case "get online supports":
                            dataOutputStream.writeUTF(gson.toJson(onlineSupports));
                            dataOutputStream.flush();
                            break;
                        case "get last messages":
                            username = dataInputStream.readUTF();
                            Support support = (Support) AllAccountZone.getAccountByUsername(username);
                            dataOutputStream.writeUTF(gson.toJson(support.getMessages().get(currentAccount.getUsername())));
                            dataOutputStream.flush();
                            break;
                        case "send message support":
                            username = dataInputStream.readUTF();
                            sender = dataInputStream.readUTF();
                            String message = dataInputStream.readUTF();
                            if (username.equals("me")) {
                                username = currentAccount.getUsername();
                                ((Support) currentAccount).getMessages().get(sender).add(new HashMap<>(Map.of(username, message)));
                            }
                            if (sender.equals("me")) {
                                sender = currentAccount.getUsername();
                                support = (Support) AllAccountZone.getAccountByUsername(username);
                                if (support.getMessages().containsKey(sender)) {
                                    support.getMessages().get(sender).add(new HashMap<>(Map.of(sender, message)));
                                } else {
                                    support.getMessages().put(sender,
                                            new ArrayList<>(Arrays.asList(new HashMap<>(Map.of(sender, message)))));
                                }
                            }
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "get seller bid":
                            dataOutputStream.writeUTF(gson.toJson(SellerZone.getBidDetails(currentAccount)));
                            dataOutputStream.flush();
                            break;
                        case "add bid":
                            data = dataInputStream.readUTF();
                            foundListType = new TypeToken<ArrayList<String>>(){}.getType();
                            info = gson.fromJson(data, foundListType);
                            SellerZone.createBid(info, currentAccount);
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "get bid products":
                            dataOutputStream.writeUTF(gson.toJson(AllAccountZone.getBidProducts()));
                            dataOutputStream.flush();
                            break;
                        case "get bid id":
                            productId = Integer.parseInt(dataInputStream.readUTF());
                            dataOutputStream.writeUTF(SellerZone.getBidIdByProductId(productId));
                            dataOutputStream.flush();
                            break;
                        case "get bid messages":
                            int bidId = dataInputStream.readInt();
                            dataOutputStream.writeUTF(gson.toJson(SellerZone.getBidById(bidId).getMessages()));
                            dataOutputStream.flush();
                            break;
                        case "get username":
                            dataOutputStream.writeUTF(currentAccount.getUsername());
                            dataOutputStream.flush();
                            break;
                        case "get bid offers":
                            bidId = dataInputStream.readInt();
                            dataOutputStream.writeUTF(gson.toJson(SellerZone.getBidById(bidId).getOfferedPrice()));
                            dataOutputStream.flush();
                            break;
                        case "send message bid":
                            bidId = dataInputStream.readInt();
                            message = dataInputStream.readUTF();
                            SellerZone.getBidById(bidId).getMessages()
                                    .add(new HashMap<>(Map.of(currentAccount.getUsername(), message)));
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "get bid price":
                            bidId = dataInputStream.readInt();
                            dataOutputStream.writeLong(SellerZone.getBidById(bidId).getMaxPrice());
                            dataOutputStream.flush();
                            break;
                        case "offer bid price":
                            bidId = dataInputStream.readInt();
                            long offeredPrice = Long.parseLong(dataInputStream.readUTF());
                            dataOutputStream.writeUTF(SellerZone.offerBidPrice(bidId, offeredPrice, currentAccount));
                            dataOutputStream.flush();
                            break;
                        case "get wallet":
                            dataOutputStream.writeLong(((Buyer) currentAccount).getWallet());
                            dataOutputStream.flush();
                            break;
                        case "get product stock":
                            productId = Integer.parseInt(dataInputStream.readUTF());
                            dataOutputStream.writeInt(SellerZone.getProductById(productId).getGeneralFeature().getStockStatus());
                            dataOutputStream.flush();
                            break;
                        case "has file in cart":
                            dataOutputStream.writeBoolean(BuyerZone.hasFileInCart(currentAccount));
                            dataOutputStream.flush();
                            break;
                        case "get files id in cart":
                            dataOutputStream.writeUTF(gson.toJson(BuyerZone.getFilesInCart(currentAccount)));
                            dataOutputStream.flush();
                            break;
                        case "exit":
                            FileProcess.writeDataBaseOnFile();
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break outerLoop;
                    }
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new DataBase();
        if (new File("resources\\admins.json").exists()) {
            FileProcess.initialize();
        }
        setStaticValues();
        try {
            ServerSocket serverSocket = new ServerSocket(storeServerPort);
            while (true) {
                Socket socket = serverSocket.accept();
                DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                String input;
                do {
                    input = dataInputStream.readUTF();
                } while (input.isEmpty());
                switch (input) {
                    case "open app":
                        new AppHandler(dataOutputStream, dataInputStream).start();
                        dataOutputStream.writeUTF("done");
                        dataOutputStream.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setStaticValues() {
        if (!DataBase.getDataBase().getAllAuctions().isEmpty()) {
            Auction.setNumOfAllAuctions(DataBase.getDataBase().getAllAuctions().stream()
                    .max(Comparator.comparingInt(Auction::getId)).get().getId() + 1);
        } else {
            Auction.setNumOfAllAuctions(1);
        }
        if (!DataBase.getDataBase().getAllBids().isEmpty()) {
            Bid.setNumOfAllBids(DataBase.getDataBase().getAllBids().stream()
                    .max(Comparator.comparingInt(Bid::getId)).get().getId() + 1);
        } else {
            Bid.setNumOfAllBids(1);
        }
        if (!DataBase.getDataBase().getAllComments().isEmpty()) {
            Comment.setNumOfAllComments(DataBase.getDataBase().getAllComments().stream()
                    .max(Comparator.comparingInt(Comment::getId)).get().getId() + 1);
        } else {
            Comment.setNumOfAllComments(1);
        }
        if (!DataBase.getDataBase().getAllLogs().isEmpty()) {
            ExchangeLog.setNumOfAllLogs(DataBase.getDataBase().getAllLogs().stream()
                    .max(Comparator.comparingInt(ExchangeLog::getId)).get().getId() + 1);
        } else {
            ExchangeLog.setNumOfAllLogs(1);
        }
        if (!DataBase.getDataBase().getAllProducts().isEmpty()) {
            Product.setNumOfAllProducts(DataBase.getDataBase().getAllProducts().stream()
                    .max(Comparator.comparingInt(Product::getId)).get().getId() + 1);
        } else {
            Product.setNumOfAllProducts(1);
        }
        if (!DataBase.getDataBase().getAllRequests().isEmpty()) {
            Request.setNumOfAllRequest(DataBase.getDataBase().getAllRequests().stream()
                    .max(Comparator.comparingInt(Request::getId)).get().getId() + 1);
        } else {
            Request.setNumOfAllRequest(1);
        }
    }
}
