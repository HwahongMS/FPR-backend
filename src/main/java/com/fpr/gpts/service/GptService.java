package com.fpr.gpts.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fpr.financialProduct.dto.FinancialGoalConsultRequest;
import com.fpr.financialProduct.dto.TopFinancialProduct;
import com.fpr.gpts.dto.ChatGPTRequestDTO;
import com.fpr.gpts.dto.ChatGPTResponseDTO;
import com.fpr.gpts.dto.Choice;
import com.fpr.gpts.dto.Message;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Data
public class GptService {

    @Value("${gpt.api_key}")
    private String gptApiKey;

    public static String historyContent = "";
    public Message makePrompt(String content, String role) {
        StringBuilder prompt = new StringBuilder();
        prompt.append(content);
        Message message = new Message(role, prompt.toString());
        return message;
    }
    public String getKnowledge() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Message> messages = new ArrayList<>();
        // Assistant API 사용할지 Prompt를 변경할지 선택하기
        // 시스템 역할 설정
        messages.add(makePrompt("너는 금융전문가로서 금융 지식을 하나 알려주는 역할이야." +
                "주제 하나를 잡고 그에 대해서 설명해줘주제에는 쌍따옴표로 강조해줘", "system"));
        // 사용자의 질문
        messages.add(makePrompt("나의 나이는 25살이야 나에게 필요한 오늘의 금융 지식을 자세히 천천히 알려줘. " +
                "대신"+historyContent+" 주제는 제외하고 알려줘 " +
                "응답은 다음과 같은 형식으로 해줘\n" +
                "\n" +
                "주제\n" +
                "\n" +
                "내용", "user"));
        // 어시스턴스
        messages.add(makePrompt("투자 다각화\n" +
                "\n" +
                "투자 다각화는 투자자의 위험을 분산시키기 위해 여러 다양한 자산에 투자하는 전략을 말합니다. " +
                "25살이라면 투자 다각화를 통해 장기적으로 안정적인 수익을 얻을 수 있습니다. " +
                "주식, 채권, 부동산, 현금 등 다양한 자산군에 투자함으로써 특정 자산의 부정적인 영향을 최소화할 수 있습니다. " +
                "꾸준히 투자 경험을 쌓고 다양한 자산에 분산 투자하여 재무 안정성을 높이는 것이 중요합니다.", "assistant"));
        messages.add(makePrompt("나의 나이는 25살이야 나에게 필요한 오늘의 금융 지식을 자세히 천천히 알려줘. " +
                "대신"+historyContent+" 주제는 제외하고 알려줘 " +
                "응답은 다음과 같은 형식으로 해줘\n" +
                "\n" +
                "주제\n" +
                "\n" +
                "내용", "user"));
        // temperature 는 답변의 창의성을 나타냄 온도가 낮을수록 정보성의 글
        ChatGPTRequestDTO chatGptRequest = new ChatGPTRequestDTO("gpt-4", messages, 0.3);
        String input = null;
        input = mapper.writeValueAsString(chatGptRequest);
        System.out.println(input);
        System.out.println("apikey : " + gptApiKey);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + gptApiKey)
                .POST(HttpRequest.BodyPublishers.ofString(input))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response.body());
        ObjectMapper objectMapper = new ObjectMapper();
        ChatGPTResponseDTO chatGPTResponseDTO = objectMapper.readValue(response.body(), ChatGPTResponseDTO.class);
        List<Choice> choices = chatGPTResponseDTO.getChoices();

        String content = choices.get(0).getMessage().getContent();
        String subject = "";
        try {
            String[] splitContent = content.split("\n");
            subject = splitContent[0];
            historyContent = historyContent + "," + splitContent[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("historyContent = " + historyContent);
        System.out.println("subject = " + subject);
        System.out.println("content = " + content);
        return response.body();
    }
    public String createConsult(FinancialGoalConsultRequest financialGoalConsultRequest, List<TopFinancialProduct> resultDeposit,List<TopFinancialProduct> resultSaving) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Message> messages = new ArrayList<>();
        // 시스템 역할 설정
        messages.add(makePrompt("너는 금융상품에 대한 이해가 높고, 분석할 줄 아는 전문가야. 주어진 금융 상품에서 금융 목표에 맞는 금융 상품을 추천해줘." +
                "그리고 금융 목표를 이룰 수 있는 금융 목표 관리 일정도 짜줘 ", "system"));

        messages.add(makePrompt("나의 금융 목표는 다음과 같아. 금융 목표를 보고 적금이 더 나은지, 예금이 더 나은지 너가 알려줘. 이거에 대한 이유도 자세히 알려줘. 그리고 내가 주어준 금융 상품중에서 어떤걸 골라야 가장 효율적으로 금융 목표에 도달할 수 있는지, 일정에 대해서도 자세히 천천히 알려줘. 만약 월 여유금으로 목표에 도달할 수 없다면, 월 지출에서 차감해서 일정을 짜줘. 이때 차감한 금액과 차감한 금액만큼 절약하기 위한 소비 습관도 자세히 천천히 알려줘\n" +
                "\n" +
                "금융 목표 : 목돈 굴리기 목표 금액 : 70000000 금융 목표 기간 : 2024-10-31 ~ 2028-12-31 나이 : 40 월 수입 : 6000000 월 저축 가능 금액 : 4000000 [ { \"koreanCompanyName\": \"전북은행\", \"financialProductName\": \"JB 다이렉트예금통장\\n(만기일시지급식)\", \"maturityInterest\": \"만기후 1개월 이하 : 만기일 현재 계약기간별 정기예금 실행이율 1/2\\n만기후 1개월 초과 : 연 0.01%\", \"joinMember\": \"실명의 개인(임의단체 제외\", \"etcNote\": \"가입금액 1계좌당 1백만원이상 10억원이하,\\n1인당 총 10억원 이하,\\n인터넷/스마트폰뱅킹 가입상품\", \"financialTypeName\": \"예금\", \"interestRateTypeName\": \"단리\", \"saveTerm\": \"12\", \"interestRate\": 3.8 }, { \"koreanCompanyName\": \"수협은행\", \"financialProductName\": \"헤이(Hey)정기예금\", \"maturityInterest\": \"* 만기후 1년 이내\\n - 만기당시 일반정기예금(월이자지급식 기준) 계약기간별 기본금리 1/2\\n* 만기후 1년 초과\\n - 만기당시 보통예금 기본금리\", \"joinMember\": \"실명의 개인\", \"etcNote\": \"-1인 다계좌 가능.\\n 단, 합산금액 최대 2억원 이내\\n-최저 10만원 이상\", \"financialTypeName\": \"예금\", \"interestRateTypeName\": \"단리\", \"saveTerm\": \"12\", \"interestRate\": 3.72 }, { \"koreanCompanyName\": \"전북은행\", \"financialProductName\": \"JB 123 정기예금\\n (만기일시지급식)\", \"maturityInterest\": \"만기후 1개월 이하 : 만기일 현재 계약기간별 정기예금 실행이율 1/2\\n만기후 1개월 초과 : 연 0.01%\", \"joinMember\": \"실명의 개인 또는 개인사업자 (1인 다계좌 가입 가능함)\", \"etcNote\": \"예금의 신규 : 인터넷뱅킹, 모바일뱅킹, 모바일웹, BDT\\n예금의 해지 : 인터넷뱅킹, 모바일뱅킹, 영업점\\n가입금액 최저 1백만원이상 고객별 5억원 이사 (다만 자동재예치시 이자 원가로 인한 5억원 초과는 가능), 계좌수 관계없이 가입가능\", \"financialTypeName\": \"예금\", \"interestRateTypeName\": \"단리\", \"saveTerm\": \"12\", \"interestRate\": 3.7 }, { \"koreanCompanyName\": \"주식회사 케이뱅크\", \"financialProductName\": \"코드K 자유적금\", \"maturityInterest\": \"만기 후 \\n- 1개월 이내: 만기시점 기본금리 X 50% \\n- 1개월 초과 ~ 6개월 이내: 만기시점 기본금리 X 30%\\n- 6개월 초과: 연 0.20%\", \"joinMember\": \"만 17세 이상 실명의 개인 및 개인사업자\", \"etcNote\": \"가입금액: 1만원 이상 30만원 이하\\n가입기간: 6개월, 1년, 2년, 3년 \\n(1인 최대 3계좌)\", \"financialTypeName\": \"적금\", \"interestRateTypeName\": \"단리\", \"saveTerm\": \"24\", \"interestRate\": 4.35 }, { \"koreanCompanyName\": \"주식회사 케이뱅크\", \"financialProductName\": \"주거래우대 자유적금\", \"maturityInterest\": \"만기 후 \\n- 1개월 이내: 만기시점 기본금리 X 50% \\n- 1개월 초과 ~ 6개월 이내: 만기시점 기본금리 X 30%\\n- 6개월 초과: 연 0.20%\", \"joinMember\": \"만 17세 이상 실명의 개인 및 개인사업자\", \"etcNote\": \"가입금액: 1천원 이상 300만원 이하\\n가입기간: 6개월 ~ 36개월\\n(1인 최대 3계좌)\", \"financialTypeName\": \"적금\", \"interestRateTypeName\": \"단리\", \"saveTerm\": \"12\", \"interestRate\": 4.2 }, { \"koreanCompanyName\": \"우리은행\", \"financialProductName\": \"WON적금\", \"maturityInterest\": \"만기 후\\n- 1개월이내 : 만기시점약정이율×50%\\n- 1개월초과 6개월이내: 만기시점약정이율×30%\\n- 6개월초과 : 만기시점약정이율×20%\\n\\n※ 만기시점 약정이율 : 일반정기적금 금리\", \"joinMember\": \"실명의 개인\", \"etcNote\": \"1. 가입기간 : 1년\\n2. 가입금액 : 월 50만원 이내\", \"financialTypeName\": \"적금\", \"interestRateTypeName\": \"단리\", \"saveTerm\": \"12\", \"interestRate\": 4.0 } ]\n" +
                "\n" +
                "응답은 다음과 같은 형태로 해줘 추천하는 상품 형태(예금/적금) 추천하는 이유 추천하는 금융 상품 이름 금융 목표 달성을 위한 저축금액 총 저축액 총 이자액 최종 금액","user"));
        messages.add(makePrompt("추천하는 상품 형태: 적금\n" +
                "추천하는 이유:\n" +
                "적금은 규칙적으로 일정 금액을 저축할 수 있으며, 상대적으로 높은 이자율을 제공합니다. 귀하의 경우, 목표 금액을 달성하기 위해 꾸준한 저축이 필요한 상황입니다. 제시된 금융 상품 중에서 \"코드K 자유적금\"이 가장 높은 이자율을 제공하므로, 귀하의 목표 달성에 가장 효율적일 것입니다.\n" +
                "\n" +
                "추천하는 금융 상품 이름: 코드K 자유적금\n" +
                "가입기간: 24개월\n" +
                "이자율: 4.35%\n" +
                "금융 목표 달성을 위한 저축금액 계산:\n" +
                "목표 기간은 약 50개월입니다. 적금의 경우, 24개월 만기 후 재가입을 고려해야 합니다. 초기 24개월 동안 매월 4,000,000원을 저축하고, 이후 만기금을 포함하여 다시 적금에 가입합니다.\n" +
                "\n" +
                "월 저축액: 4,000,000원\n" +
                "총 저축액 (24개월 기준): 96,000,000원\n" +
                "총 이자액 (첫 24개월 기준): 약 8,496,000원 (대략적 계산)\n" +
                "첫 24개월 후 총 금액: 약 104,496,000원\n" +
                "이 계산은 대략적인 예상치이며, 실제 이자 계산 방식(단리, 복리, 세금 공제 등)에 따라 달라질 수 있습니다. 또한, 만기 후 재가입 시 시중 이자율 변동을 고려해야 합니다.\n" +
                "\n" +
                "최종 금액:\n" +
                "목표 기간 내에 목표 금액 70,000,000원을 초과하여 달성 가능합니다. 다만, 실제 최종 금액은 이자율 변동, 세금, 추가 저축 여부에 따라 달라질 수 있습니다.\n" +
                "\n" +
                "월 지출에서 차감하여 저축금액을 조정할 필요는 없어 보입니다. 이미 월 저축 가능 금액이 충분히 높기 때문입니다. 그러나 비상금을 위해 일부 금액은 유동적으로 관리하는 것이 좋습니다. 소비 습관을 개선하기 위해서는 불필요한 지출을 줄이고, 정기적으로 지출을 검토하여 저축 목표를 유지하는 것이 중요합니다.","assistant"));
        StringBuilder sb = new StringBuilder();
        sb.append("나의 금융 목표는 다음과 같아. 금융 목표를 보고 적금이 더 나은지, 예금이 더 나은지 너가 알려줘. " +
                "이거에 대한 이유도 자세히 알려줘. " +
                "그리고 내가 주어준 금융 상품중에서 어떤걸 골라야 가장 효율적으로 금융 목표에 도달할 수 있는지, 일정에 대해서도 자세히 천천히 알려줘. " +
                "만약 월 여유금으로 목표에 도달할 수 없다면, 월 지출에서 차감해서 일정을 짜줘. 이때 차감한 금액과 차감한 금액만큼 절약하기 위한 소비 습관도 자세히 천천히 알려줘.");
        sb.append("금융 목표 : "+financialGoalConsultRequest.getUserGoal());
        sb.append("목표 금액 : "+financialGoalConsultRequest.getGoalMoney());
        sb.append("금융 목표 기간 : "+financialGoalConsultRequest.getGoalStart()+" ~ "+financialGoalConsultRequest.getGoalEnd());
        sb.append("나이 : "+ financialGoalConsultRequest.getUserGoal());
        sb.append("월 수입 : "+financialGoalConsultRequest.getMonthIncome());
        sb.append("월 소비 : "+financialGoalConsultRequest.getMonthConsume());
        sb.append("금융 상품 목록 \n");
        for(TopFinancialProduct s : resultDeposit)
            sb.append(s.toString());
        for(TopFinancialProduct s : resultSaving)
            sb.append(s.toString());
        messages.add(makePrompt(sb.toString(),"user"));
        ChatGPTRequestDTO chatGptRequest = new ChatGPTRequestDTO("gpt-4", messages, 0.3);
        String input = null;
        input = mapper.writeValueAsString(chatGptRequest);
        System.out.println(input);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + gptApiKey)
                .POST(HttpRequest.BodyPublishers.ofString(input))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response.body());
        ObjectMapper objectMapper = new ObjectMapper();
        ChatGPTResponseDTO chatGPTResponseDTO = objectMapper.readValue(response.body(), ChatGPTResponseDTO.class);
        List<Choice> choices = chatGPTResponseDTO.getChoices();

        String content = choices.get(0).getMessage().getContent();
        return content;
    }



}
