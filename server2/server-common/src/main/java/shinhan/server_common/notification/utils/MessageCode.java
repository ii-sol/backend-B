package shinhan.server_common.notification.utils;

import java.util.HashMap;
import java.util.Map;

public class MessageCode {
    private static final Map<Integer, String> messageCodes = new HashMap<>();

    static {
        messageCodes.put(0, "%s님이 아이 등록을 요청했어요.");
        messageCodes.put(1, "%s님이 부모 등록을 요청했어요.");
        messageCodes.put(101, "%s님이 %d원을 송금하였습니다.");
        messageCodes.put(200, "%s님으로부터 정기 용돈이 시작돼요.");
        messageCodes.put(201, "%s님에게 정기 용돈 %d원을 보냈어요.");
        messageCodes.put(202, "%s님으로부터 정기 용돈 %d원이 들어왔어요.");
        messageCodes.put(210, "%s님이 용돈 조르기를 요청했어요.");
        messageCodes.put(211, "%s님이 용돈 조르기를 거절했어요.");
        messageCodes.put(212, "%s님이 용돈 조르기를 수락했어요.");
        messageCodes.put(220, "%s님으로부터 용돈 %d원이 들어왔어요.");
        messageCodes.put(230, "용돈 조르기를 사용하여 신뢰도가 -1 되었어요.");
        messageCodes.put(300, "%s님이 미션 완료를 요청했어요.");
        messageCodes.put(301, "%s님이 미션 완료(%s)를 확인했어요. %d원이 들어왔어요.");
        messageCodes.put(310, "%s님이 미션을 요청했어요.");
        messageCodes.put(311, "%s님이 미션을 포기했어요.");
        messageCodes.put(312, "%s님이 미션을 수락했어요.");
        messageCodes.put(320, "%s님이 미션을 요청했어요.");
        messageCodes.put(321, "%s님이 미션을 거절했어요.");
        messageCodes.put(322, "%s님이 미션을 수락했어요.");
        messageCodes.put(330, "미션을 수행하여 신뢰도가 +1 되었어요.");
        messageCodes.put(401, "%s님이 %d원 %d개월 대출을 신청했어요.");
        messageCodes.put(402, "%s님이 %d원 %d개월 대출을 수락했어요.");
        messageCodes.put(403, "%s님이 %d원 %d개월 대출을 거절했어요.");
        messageCodes.put(411, "%s님이 %d원을 상환했어요.");
        messageCodes.put(412, "%s님에게 %d원을 상환했어요.");
        messageCodes.put(413, "%s님이 잔고 부족으로 대출 상환을 실패했어요.");
        messageCodes.put(414, "%s님에게 잔고 부족으로 대출 상환을 못했어요.");
        messageCodes.put(420, "대출을 상환하여 신뢰도가 +5 되었어요.");
        messageCodes.put(421, "대출을 연체하여 신뢰도가 -1 되었어요.");
        messageCodes.put(431, "신뢰가 쌓여 신뢰도가 매우 높음이 되었어요.");
        messageCodes.put(432, "신뢰가 쌓여 신뢰도가 높음이 되었어요.");
        messageCodes.put(433, "신뢰도가 쌓여 신뢰도가 보통이 되었어요.");
        messageCodes.put(434, "신뢰도가 쌓여 신뢰도가 낮음이 되었어요.");
        messageCodes.put(435, "신뢰가 사라져 신뢰도가 높음이 되었어요.");
        messageCodes.put(436, "신뢰가 사라져 신뢰도가 보통이 되었어요.");
        messageCodes.put(437, "신뢰가 사라져 신뢰도가 낮음이 되었어요.");
        messageCodes.put(438, "신뢰가 사라져 신뢰도가 매우 낮음이 되었어요.");
        messageCodes.put(441, "%s님이 신뢰가 쌓여 신뢰도가 매우 높음이 되었어요.");
        messageCodes.put(442, "%s님이 신뢰가 쌓여 신뢰도가 높음이 되었어요.");
        messageCodes.put(443, "%s님이 신뢰도가 쌓여 신뢰도가 보통이 되었어요.");
        messageCodes.put(444, "%s님이 신뢰도가 쌓여 신뢰도가 낮음이 되었어요.");
        messageCodes.put(445, "%s님이 신뢰가 사라져 신뢰도가 높음이 되었어요.");
        messageCodes.put(446, "%s님이 신뢰가 사라져 신뢰도가 보통이 되었어요.");
        messageCodes.put(447, "%s님이 신뢰가 사라져 신뢰도가 낮음이 되었어요.");
        messageCodes.put(448, "%s님이 신뢰가 사라져 신뢰도가 매우 낮음이 되었어요.");
        messageCodes.put(501, "%s님이 보유하신 %s종목이 10프로 떨어졌어요.");
        messageCodes.put(502, "%s님이 보유하신 %s종목이 30프로 떨어졌어요.");
        messageCodes.put(503, "%s님이 보유하신 %s종목이 50프로 떨어졌어요.");
        messageCodes.put(511, "%s님이 종목 리스트에 %s을 제안했어요!");
        messageCodes.put(512, "%s님이 종목 리스트에 %s 제안을 수락했어요.");
        messageCodes.put(513, "%s님이 종목 리스트에 %s 제안을 거절했어요.");
        messageCodes.put(514, "%s님이 %s을 0주 %s 제안했어요!");
        messageCodes.put(515, "%s님이 %s을 0주 %s 제안을 수락했어요.");
        messageCodes.put(516, "%s님이 %s을 0주 %s 제안을 거절했어요.");
        messageCodes.put(517, "%s님이 종목리스트에 %s을 추가했어요.");
        messageCodes.put(520, "투자 수익이 10%% 이상이어서 신뢰도가 +5 되었어요.");
        messageCodes.put(521, "투자 손실이 10%% 이상이어서 신뢰도가 -5 되었어요.");
    }

    public static String getMessage(Integer code) {
        return messageCodes.get(code);
    }
}
