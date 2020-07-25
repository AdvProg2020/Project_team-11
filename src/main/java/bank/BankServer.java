package bank;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class BankServer {
    private static final int BANK_SERVER_PORT = 9999;
    private static HashMap<String, String> userAndToken = new HashMap<>();

    static class ClientHandler extends Thread {
        private DataOutputStream dataOutputStream;
        private DataInputStream dataInputStream;

        public ClientHandler(DataOutputStream dataOutputStream, DataInputStream dataInputStream) {
            this.dataOutputStream = dataOutputStream;
            this.dataInputStream = dataInputStream;
        }

        @Override
        public void run() {
            outerLoop :
            while (true) {
                try {
                    String input;
                    do {
                        input = dataInputStream.readUTF();
                    } while (input.isEmpty());
                    System.out.println(input);
                    String[] splitInfo = input.split("\\s");
                    switch (splitInfo[0]) {
                        case "create_account":
                            if (splitInfo.length == 6) {
                                dataOutputStream.writeUTF(Account.createAccount(splitInfo[1],
                                        splitInfo[2],
                                        splitInfo[3],
                                        splitInfo[4],
                                        splitInfo[5]));
                            } else {
                                dataOutputStream.writeUTF("invalid parameter passed");
                            }
                            dataOutputStream.flush();
                            break;
                        case "get_token":
                            if (splitInfo.length == 3) {
                                String result = Account.getToken(splitInfo[1], splitInfo[2]);
                                if (!result.startsWith("invalid")) {
                                    userAndToken.put(result, splitInfo[1]);
                                    new Thread(() -> {
                                        try {
                                            Thread.sleep(3600 * 1000);
                                            userAndToken.remove(splitInfo[1]);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }).start();
                                }
                                dataOutputStream.writeUTF(result);
                            } else {
                                dataOutputStream.writeUTF("invalid parameter passed");
                            }
                            dataOutputStream.flush();
                            break;
                        case "create_receipt":
                            if (splitInfo.length == 7) {
                                if (userAndToken.containsKey(splitInfo[1])) {
                                    dataOutputStream.writeUTF(Account.createReceipt(userAndToken.get(splitInfo[1]),
                                            splitInfo[2],
                                            splitInfo[3],
                                            splitInfo[4],
                                            splitInfo[5],
                                            splitInfo[6]));
                                } else {
                                    dataOutputStream.writeUTF("token expired");
                                }
                            } else if (splitInfo.length == 6) {
                                if (userAndToken.containsKey(splitInfo[1])) {
                                    dataOutputStream.writeUTF(Account.createReceipt(userAndToken.get(splitInfo[1]),
                                            splitInfo[2],
                                            splitInfo[3],
                                            splitInfo[4],
                                            splitInfo[5],
                                            ""));
                                } else {
                                    dataOutputStream.writeUTF("token expired");
                                }
                            } else {
                                dataOutputStream.writeUTF("invalid parameter passed");
                            }
                            dataOutputStream.flush();
                            break;
                        case "get_transactions":
                            if (splitInfo.length == 3) {
                                if (userAndToken.containsKey(splitInfo[1])) {
                                    dataOutputStream.writeUTF(Account.getTransactions(userAndToken.get(splitInfo[1]),
                                            splitInfo[2]));
                                } else {
                                    dataOutputStream.writeUTF("token expired");
                                }
                            } else {
                                dataOutputStream.writeUTF("invalid parameter passed");
                            }
                            dataOutputStream.flush();
                            break;
                        case "pay":
                            if (splitInfo.length == 2) {
                                dataOutputStream.writeUTF(Account.payReceipt(splitInfo[1]));
                            } else {
                                dataOutputStream.writeUTF("invalid parameter passed");
                            }
                            dataOutputStream.flush();
                            break;
                        case "get_balance":
                            if (splitInfo.length == 2) {
                                if (userAndToken.containsKey(splitInfo[1])) {
                                    dataOutputStream.writeUTF(String.valueOf(
                                            Account.getAccountByUsername(userAndToken.get(splitInfo[1])).getBalance()));
                                } else {
                                    dataOutputStream.writeUTF("token expired");
                                }
                            } else {
                                dataOutputStream.writeUTF("invalid parameter passed");
                            }
                            dataOutputStream.flush();
                            break;
                        case "exit":
                            if (splitInfo.length == 1) {
                                FileProcess.writeDataOnFile();
                                dataOutputStream.writeUTF("done");
                                dataOutputStream.flush();
                                break outerLoop;
                            } else {
                                dataOutputStream.writeUTF("invalid parameter passed");
                            }
                            dataOutputStream.flush();
                            break;
                        default:
                            dataOutputStream.writeUTF("invalid input");
                            dataOutputStream.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        if (new File("resources\\bank\\accounts.json").exists()) {
            FileProcess.initialize();
        }
        try {
            ServerSocket serverSocket = new ServerSocket(BANK_SERVER_PORT);
            int counter = 1000;
            while (true) {
                Socket socket = serverSocket.accept();
                DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                dataOutputStream.writeUTF("hello " + counter++);
                dataOutputStream.flush();
                new ClientHandler(dataOutputStream, dataInputStream).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
