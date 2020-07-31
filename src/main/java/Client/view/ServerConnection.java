package Client.view;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static Client.view.ClientHandler.getDataInputStream;
import static Client.view.ClientHandler.getDataOutputStream;

public class ServerConnection {
    private static Gson gson = new Gson();

    public static boolean checkUsername(String username) throws IOException {
        getDataOutputStream().writeUTF("check username");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(username);
        getDataOutputStream().flush();
        return getDataInputStream().readBoolean();
    }

    public static void register(ArrayList<String> info) throws IOException {
        getDataOutputStream().writeUTF("register");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(gson.toJson(info));
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static String signIn(ArrayList<String> info) throws IOException {
        getDataOutputStream().writeUTF("sign in");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(gson.toJson(info));
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static void logout() throws IOException {
        getDataOutputStream().writeUTF("logout");
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static void editPersonalInfo(String field, String newValue) throws IOException {
        getDataOutputStream().writeUTF("edit personal info");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(field);
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(newValue);
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static boolean checkDiscountCode(String code) throws IOException {
        getDataOutputStream().writeUTF("check discount code");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(code);
        getDataOutputStream().flush();
        return getDataInputStream().readBoolean();
    }

    public static void removeDiscount(String code) throws IOException {
        getDataOutputStream().writeUTF("remove discount");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(code);
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static void editDiscount(String field, String newValue, String discountCode) throws IOException {
        getDataOutputStream().writeUTF("edit discount");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(field);
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(newValue);
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(discountCode);
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static boolean checkCategoryName (String name) throws IOException {
        getDataOutputStream().writeUTF("check category name");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(name);
        getDataOutputStream().flush();
        return getDataInputStream().readBoolean();
    }

    public static void editCategory(String field, String newValue, String categoryName, String lastField) throws IOException {
        getDataOutputStream().writeUTF("edit category");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(field);
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(newValue);
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(categoryName);
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(lastField);
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static void editProduct(String description) throws IOException {
        getDataOutputStream().writeUTF("edit product");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(description);
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static String addProduct(ArrayList<String> info, HashMap<String, String> features) throws IOException {
        getDataOutputStream().writeUTF("add product");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(gson.toJson(info));
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(gson.toJson(features));
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static void addAuction(ArrayList<String> info) throws IOException {
        getDataOutputStream().writeUTF("add auction");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(gson.toJson(info));
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static void editAuction(String field, String newValue, int auctionId) throws IOException {
        getDataOutputStream().writeUTF("edit auction");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(field);
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(newValue);
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(String.valueOf(auctionId));
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static boolean checkProductId(int productId) throws IOException {
        getDataOutputStream().writeUTF("check product id");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(String.valueOf(productId));
        getDataOutputStream().flush();
        return getDataInputStream().readBoolean();
    }

    public static String canCompareProduct(int productId1, int productId2) throws IOException {
        getDataOutputStream().writeUTF("can compare product");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(String.valueOf(productId1));
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(String.valueOf(productId2));
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static void editBankOperation(String field, String newValue) throws IOException {
        getDataOutputStream().writeUTF("edit bank operation");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(field);
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(newValue);
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static boolean sellerHasProduct(String productId) throws IOException {
        getDataOutputStream().writeUTF("seller has product");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(productId);
        getDataOutputStream().flush();
        return getDataInputStream().readBoolean();
    }

    public static int getProductStock(String productId) throws IOException {
        getDataOutputStream().writeUTF("get product stock");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(productId);
        getDataOutputStream().flush();
        return getDataInputStream().readInt();
    }

    public static void addBid(ArrayList<String> info) throws IOException {
        getDataOutputStream().writeUTF("add bid");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(gson.toJson(info));
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static long getWallet() throws IOException {
        getDataOutputStream().writeUTF("get wallet");
        getDataOutputStream().flush();
        return getDataInputStream().readLong();
    }

    public static long getBidPrice(int bidId) throws IOException {
        getDataOutputStream().writeUTF("get bid price");
        getDataOutputStream().flush();
        getDataOutputStream().writeInt(bidId);
        getDataOutputStream().flush();
        return getDataInputStream().readLong();
    }

    public static String offerBidPrice(int bidId, String offeredPrice) throws IOException {
        getDataOutputStream().writeUTF("offer bid price");
        getDataOutputStream().flush();
        getDataOutputStream().writeInt(bidId);
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(offeredPrice);
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static String getPersonalInfo() throws IOException {
        getDataOutputStream().writeUTF("get personal info");
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static void deleteUser(String username) throws IOException {
        getDataOutputStream().writeUTF("delete user");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(username);
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static String getDiscounts() throws IOException {
        getDataOutputStream().writeUTF("get discounts");
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static String getDiscountInfo(String code) throws IOException {
        getDataOutputStream().writeUTF("get discount info");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(code);
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static boolean getBuyer(String username) throws IOException {
        getDataOutputStream().writeUTF("get buyer");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(username);
        getDataOutputStream().flush();
        return getDataInputStream().readBoolean();
    }

    public static void createDiscount(ArrayList<String> discountInfo, ArrayList<String> allowedUsers) throws IOException {
        getDataOutputStream().writeUTF("create discount");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(gson.toJson(discountInfo));
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(gson.toJson(allowedUsers));
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static void removeProduct(int productId) throws IOException {
        getDataOutputStream().writeUTF("remove product");
        getDataOutputStream().flush();
        getDataOutputStream().writeInt(productId);
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static String getRequests() throws IOException {
        getDataOutputStream().writeUTF("get requests");
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static void removeRequest(String requestId) throws IOException {
        getDataOutputStream().writeUTF("remove request");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(requestId);
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static void acceptRequest(String requestId) throws IOException {
        getDataOutputStream().writeUTF("accept request");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(requestId);
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static void declineRequest(String requestId) throws IOException {
        getDataOutputStream().writeUTF("decline request");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(requestId);
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static String getCategories() throws IOException {
        getDataOutputStream().writeUTF("get categories");
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static String getCategoryFeature(String categoryName) throws IOException {
        getDataOutputStream().writeUTF("get category feature");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(categoryName);
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static void removeCategory(String categoryName) throws IOException {
        getDataOutputStream().writeUTF("remove category");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(categoryName);
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static void createCategory(String name, ArrayList<String> feature) throws IOException {
        getDataOutputStream().writeUTF("create category");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(name);
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(gson.toJson(feature));
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static String getBankOperation() throws IOException {
        getDataOutputStream().writeUTF("get bank operations");
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static String createWithdrawReceipt(long money, long accountId) throws IOException {
        getDataOutputStream().writeUTF("create withdraw receipt");
        getDataOutputStream().flush();
        getDataOutputStream().writeLong(money);
        getDataOutputStream().flush();
        getDataOutputStream().writeLong(accountId);
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static String payReceipt(int receiptId) throws IOException {
        getDataOutputStream().writeUTF("pay receipt");
        getDataOutputStream().flush();
        getDataOutputStream().writeInt(receiptId);
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static void decreaseMoney(long money, long accountId) throws IOException {
        getDataOutputStream().writeUTF("decrease money");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(String.valueOf(money));
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(String.valueOf(accountId));
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static String getAllBuyLog() throws IOException {
        getDataOutputStream().writeUTF("get all buy logs");
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static String getBuyLogAddress() throws IOException {
        getDataOutputStream().writeUTF("get buy log address");
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static void sendBuyLog(String buyLogId) throws IOException {
        getDataOutputStream().writeUTF("send buy log");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(buyLogId);
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static String getBidProducts() throws IOException {
        getDataOutputStream().writeUTF("get bid products");
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static String getBidId(String productId) throws IOException {
        getDataOutputStream().writeUTF("get bid id");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(productId);
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static boolean isBuyer() throws IOException {
        getDataOutputStream().writeUTF("is buyer");
        getDataOutputStream().flush();
        return getDataInputStream().readBoolean();
    }

    public static String getBidMessages(int bidId) throws IOException {
        getDataOutputStream().writeUTF("get bid messages");
        getDataOutputStream().flush();
        getDataOutputStream().writeInt(bidId);
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static String getUsername() throws IOException {
        getDataOutputStream().writeUTF("get username");
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static void sendMessageBid(int bidId, String message) throws IOException {
        getDataOutputStream().writeUTF("send message bid");
        getDataOutputStream().flush();
        getDataOutputStream().writeInt(bidId);
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(message);
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static String getBidOffers(int bidId) throws IOException {
        getDataOutputStream().writeUTF("get bid offers");
        getDataOutputStream().flush();
        getDataOutputStream().writeInt(bidId);
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static String createChargeReceipt(String money) throws IOException {
        getDataOutputStream().writeUTF("create charge receipt");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(money);
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static void increaseMoney(String money) throws IOException {
        getDataOutputStream().writeUTF("increase money");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(money);
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static String getPriceWithAuction() throws IOException {
        getDataOutputStream().writeUTF("price with auction");
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static String checkDiscount(String code) throws IOException {
        getDataOutputStream().writeUTF("check discount");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(code);
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static String getProductInCart() throws IOException {
        getDataOutputStream().writeUTF("product in cart");
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static String getProductName(int productId) throws IOException {
        getDataOutputStream().writeUTF("get product name");
        getDataOutputStream().flush();
        getDataOutputStream().writeInt(productId);
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static String getAuctionPrice(int productId) throws IOException {
        getDataOutputStream().writeUTF("get auction price");
        getDataOutputStream().flush();
        getDataOutputStream().writeInt(productId);
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static boolean changeNumber(int productId, int amount) throws IOException {
        getDataOutputStream().writeUTF("change number");
        getDataOutputStream().flush();
        getDataOutputStream().writeInt(productId);
        getDataOutputStream().flush();
        getDataOutputStream().writeInt(amount);
        getDataOutputStream().flush();
        return getDataInputStream().readBoolean();
    }

    public static void removeProductInCart() throws IOException {
        getDataOutputStream().writeUTF("remove product in cart");
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static long getBuyerPrice() throws IOException {
        getDataOutputStream().writeUTF("buyer price");
        getDataOutputStream().flush();
        return getDataInputStream().readLong();
    }

    public static boolean hasFileInCart() throws IOException {
        getDataOutputStream().writeUTF("has file in cart");
        getDataOutputStream().flush();
        return getDataInputStream().readBoolean();
    }

    public static boolean canPay() throws IOException {
        getDataOutputStream().writeUTF("can pay");
        getDataOutputStream().flush();
        return getDataInputStream().readBoolean();
    }

    public static String getFilesIdInCart() throws IOException {
        getDataOutputStream().writeUTF("get files id in cart");
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static void pay(String type, String address, String phoneNumber) throws IOException {
        getDataOutputStream().writeUTF("pay");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(type);
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(address);
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(phoneNumber);
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static long getMinMoney() throws IOException {
        getDataOutputStream().writeUTF("get min money");
        getDataOutputStream().flush();
        return getDataInputStream().readLong();
    }

    public static String getOrders() throws IOException {
        getDataOutputStream().writeUTF("get orders");
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static String getBuyerDiscounts() throws IOException {
        getDataOutputStream().writeUTF("get buyer discounts");
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static String getOnlineSupports() throws IOException {
        getDataOutputStream().writeUTF("get online supports");
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static String getLastSupportMessages(String supportUsername) throws IOException {
        getDataOutputStream().writeUTF("get last messages");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(supportUsername);
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static void sendMessageSupport(String supportUsername, String senderUsername, String message) throws IOException {
        getDataOutputStream().writeUTF("send message support");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(supportUsername);
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(senderUsername);
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(message);
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static void setBankOperation(String commission, String minMoney, String bankPassword) throws IOException {
        getDataOutputStream().writeUTF("set bank operation");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(commission);
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(minMoney);
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(bankPassword);
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static String getProducts() throws IOException {
        getDataOutputStream().writeUTF("get products");
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static String getProduct(int productId) throws IOException {
        getDataOutputStream().writeUTF("get product");
        getDataOutputStream().flush();
        getDataOutputStream().writeInt(productId);
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static boolean hasBoughtProduct(int productId) throws IOException {
        getDataOutputStream().writeUTF("has bought");
        getDataOutputStream().flush();
        getDataOutputStream().writeInt(productId);
        getDataOutputStream().flush();
        return getDataInputStream().readBoolean();
    }

    public static void rate(int productId, String score) throws IOException {
        getDataOutputStream().writeUTF("rate");
        getDataOutputStream().flush();
        getDataOutputStream().writeInt(productId);
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(score);
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static String compareProduct(int productId, int productId2) throws IOException {
        getDataOutputStream().writeUTF("compare product");
        getDataOutputStream().flush();
        getDataOutputStream().writeInt(productId);
        getDataOutputStream().flush();
        getDataOutputStream().writeInt(productId2);
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static void comment(String comment, int productId) throws IOException {
        getDataOutputStream().writeUTF("comment");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(comment);
        getDataOutputStream().flush();
        getDataOutputStream().writeInt(productId);
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static void addProductToCart(int productId) throws IOException {
        getDataOutputStream().writeUTF("add to cart");
        getDataOutputStream().flush();
        getDataOutputStream().writeInt(productId);
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static long getBankAccountId() throws IOException {
        getDataOutputStream().writeUTF("get bank account id");
        getDataOutputStream().flush();
        return getDataInputStream().readLong();
    }

    public static void requestWithdrawMoney(String money, long accountId) throws IOException {
        getDataOutputStream().writeUTF("request withdraw money");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(money);
        getDataOutputStream().flush();
        getDataOutputStream().writeLong(accountId);
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static String getSalesHistory() throws IOException {
        getDataOutputStream().writeUTF("get sales history");
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static String getSellerProducts() throws IOException {
        getDataOutputStream().writeUTF("get seller products");
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static void removeSellerProduct(int productId) throws IOException {
        getDataOutputStream().writeUTF("remove seller product");
        getDataOutputStream().flush();
        getDataOutputStream().writeInt(productId);
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static String getProductDetails(String productId) throws IOException {
        getDataOutputStream().writeUTF("product details");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(productId);
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static String getSellerAuctions() throws IOException {
        getDataOutputStream().writeUTF("get seller auctions");
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static String getAuctionDetails(String auctionId) throws IOException {
        getDataOutputStream().writeUTF("get auction details");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(auctionId);
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static void removeAuctionProduct(String auctionId, String productId) throws IOException {
        getDataOutputStream().writeUTF("remove auction product");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(auctionId);
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(productId);
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static boolean hasAuction(String productId) throws IOException {
        getDataOutputStream().writeUTF("has auction");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(productId);
        getDataOutputStream().flush();
        return getDataInputStream().readBoolean();
    }

    public static void addAuctionProduct(String auctionId, String productId) throws IOException {
        getDataOutputStream().writeUTF("add auction product");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(auctionId);
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(productId);
        getDataOutputStream().flush();
        getDataInputStream().readUTF();
    }

    public static String getSellerBid() throws IOException {
        getDataOutputStream().writeUTF("get seller bid");
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static String getMessageSenders() throws IOException {
        getDataOutputStream().writeUTF("get message senders");
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }

    public static String getMessages(String sender) throws IOException {
        getDataOutputStream().writeUTF("get messages");
        getDataOutputStream().flush();
        getDataOutputStream().writeUTF(sender);
        getDataOutputStream().flush();
        return getDataInputStream().readUTF();
    }
}
