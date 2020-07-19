package Server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.*;
import model.*;

import java.io.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Server {
    private static final int storeServerPort = 8888;
    private static final int bankServerPort = 9999;

    public static int getBankServerPort() {
        return bankServerPort;
    }

    static class AppHandler extends Thread {
        private DataOutputStream dataOutputStream;
        private DataInputStream dataInputStream;
        private Account currentAccount;

        public AppHandler(DataOutputStream dataOutputStream, DataInputStream dataInputStream) {
            this.dataOutputStream = dataOutputStream;
            this.dataInputStream = dataInputStream;
            this.currentAccount = null;
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
                            }
                            dataOutputStream.writeUTF(result);
                            dataOutputStream.flush();
                            break;
                        case "logout":
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
                            BuyerZone.payMoney(currentAccount);
                            dataOutputStream.writeUTF("done");
                            dataOutputStream.flush();
                            break;
                        case "get orders":
                            dataOutputStream.writeUTF(gson.toJson(BuyerZone.getOrdersInfo(currentAccount)));
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
