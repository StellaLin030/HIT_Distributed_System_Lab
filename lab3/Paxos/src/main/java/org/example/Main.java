package org.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;


// 定义接受者类
class Acceptor {
    private int maxProposalNumberSeen = -1;
    private int highestAcceptedProposalNumber = -1;
    private String highestAcceptedProposalValue = null;
    private int id;

    public Acceptor(int id, int highestAcceptedProposalNumber, String highestAcceptedProposalValue) {
        this.id = id;
        this.highestAcceptedProposalNumber = highestAcceptedProposalNumber;
        this.highestAcceptedProposalValue = highestAcceptedProposalValue;
    }

    public int receivePrepareRequest(int proposalNumber) {
        if (proposalNumber > highestAcceptedProposalNumber) {
            maxProposalNumberSeen = proposalNumber;
            System.out.println("Acceptor " + id + ": 承诺接受提案编号 " + proposalNumber);
            return 1; // 同意
        } else {
            System.out.println("Acceptor " + id + ": 拒绝提案编号 " + proposalNumber);
            return 0; // 不同意
        }
    }

    public int receiveAcceptRequest(int proposalNumber, String proposalValue, Learner learner) {
        if (proposalNumber == maxProposalNumberSeen && proposalNumber > highestAcceptedProposalNumber) {
            highestAcceptedProposalValue = proposalValue;
            highestAcceptedProposalNumber = proposalNumber;
            System.out.println("Acceptor " + id + ": 接受提案值: " + proposalValue);
            learner.recordProposal(id, proposalNumber, proposalValue);
            return 1; // 同意
        } else {
            System.out.println("Acceptor " + id + ": 拒绝提案值: " + proposalValue);
            return 0; // 不同意
        }
    }

    public int getHighestAcceptedProposalNumber() {
        return highestAcceptedProposalNumber;
    }

    public String getHighestAcceptedProposalValue() {
        return highestAcceptedProposalValue;
    }

    public int getId() {
        return id;
    }

    public void setPromisedProposalNumber(int number) {
        highestAcceptedProposalNumber = number;
    }
}

// 定义提议者类
class Proposer {
    private int proposalNumber;
    private String proposalValue;

    public Proposer(int proposalNumber, String proposalValue) {
        this.proposalNumber = proposalNumber;
        this.proposalValue = proposalValue;
    }

    public int sendPrepareRequests(List<Acceptor> acceptors) {
        int agreedCount = 0;
        for (Acceptor acceptor : acceptors) {
            agreedCount += acceptor.receivePrepareRequest(proposalNumber);
        }
        // 检查是否超过半数
        return agreedCount > acceptors.size() / 2 ? 1 : 0;
    }

    public int sendAcceptRequests(List<Acceptor> acceptors, Learner learner) {
        int agreedCount = 0;
        for (Acceptor acceptor : acceptors) {
            agreedCount += acceptor.receiveAcceptRequest(proposalNumber, proposalValue, learner);
        }
        // 检查是否超过半数
        return agreedCount > acceptors.size() / 2 ? 1 : 0;
    }
}

// 定义学习者类
class Learner {
    private Map<Integer, String> proposalsLearned = new HashMap<>();
    private Map<String, List<Integer>> proposalVoteCounts = new HashMap<>();
    private List<String> outputLog = new ArrayList<>();

    public void recordProposal(int acceptorId, int proposalNumber, String proposalValue) {
        String logEntry = "Learner 记录 Acceptor " + acceptorId + ": 提案编号: " + proposalNumber + ", 提案值: " + proposalValue;
        outputLog.add(logEntry);

        proposalsLearned.put(proposalNumber, proposalValue);

        List<Integer> proposalNumbers = proposalVoteCounts.getOrDefault(proposalValue, new ArrayList<>());
        proposalNumbers.add(proposalNumber);
        proposalVoteCounts.put(proposalValue, proposalNumbers);
    }

    public void displayLearnedProposals() {
        System.out.println("Learner 记录的提案：");
        for (String logEntry : outputLog) {
            System.out.println(logEntry);
        }

        int highestCount = -1;
        String mostCommonProposalValue = null;
        for (Map.Entry<String, List<Integer>> entry : proposalVoteCounts.entrySet()) {
            if (entry.getValue().size() > highestCount) {
                highestCount = entry.getValue().size();
                mostCommonProposalValue = entry.getKey();
            }
        }
        List<Integer> mostCommonProposalNumbers = proposalVoteCounts.get(mostCommonProposalValue);
        if (mostCommonProposalValue != null && !mostCommonProposalNumbers.isEmpty() && highestCount > 1) {
            System.out.println("提案值 '" + mostCommonProposalValue + "' 出现次数最多，为 " + highestCount + " 次，提案编号为：" + mostCommonProposalNumbers.get(0));
        } else {
            System.err.println("未达到半数！");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 创建 3 个接受者
        Acceptor[] acceptors = new Acceptor[3];
        for (int i = 0; i < acceptors.length; i++) {
            acceptors[i] = new Acceptor(i + 1, -1, null);
        }

        // 创建 1 个学习者
        Learner learner = new Learner();

        while (true) {
            System.out.print("输入提案编号（输入 -1 退出）：");
            int proposalNumber = scanner.nextInt();
            if (proposalNumber == -1) break;

            scanner.nextLine(); // 读取换行符
            System.out.print("输入提案值：");
            String proposalValue = scanner.nextLine();

            // 创建提议者并发起提案
            Proposer proposer = new Proposer(proposalNumber, proposalValue);

            // 检查承诺票数是否超过半数，若超过则继续发送接受请求
            System.err.println("提案阶段：");
            int majorityAgreed = proposer.sendPrepareRequests(Arrays.asList(acceptors));
            if (majorityAgreed == 1) {
                System.out.println("大多数接受者同意，进入接受阶段");
                System.err.println("接受阶段：");
                int acceptCount = proposer.sendAcceptRequests(Arrays.asList(acceptors), learner);
                if (acceptCount == 1) {
                    System.out.println("大多数接受者接受，进入学习阶段");
                } else {
                    System.out.println("大多数接受者不接受，提案失败");
                }
            } else {
                System.out.println("大多数接受者不同意，提案失败");
            }
            learner.displayLearnedProposals();
        }
        scanner.close();
    }
}
