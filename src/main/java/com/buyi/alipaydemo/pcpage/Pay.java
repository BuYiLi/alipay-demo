package com.buyi.alipaydemo.pcpage;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.buyi.alipaydemo.config.AlipayConfig;

import java.io.UnsupportedEncodingException;

/**
 * Created by 1132989278@qq.com on 2018/12/10 10:31
 */
public class Pay {
    public static void main(String[] args) {
        Pay pay = new Pay();
        pay.pay();

    }

    public void pay() {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id,
                AlipayConfig.merchant_private_key, AlipayConfig.format, AlipayConfig.charset,
                AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        String charset = "utf-8",
                out_trade_no,
                product_code,
                total_amount,
                subject,
                body;
        try {
            //商户订单号，商户网站订单系统中唯一订单号，必填
            out_trade_no = new String(("商户订单号" + System.currentTimeMillis()).getBytes(), charset);
            //销售产品码，与支付宝签约的产品码名称。 注：目前仅支持FAST_INSTANT_TRADE_PAY, 必填
            product_code = new String("FAST_INSTANT_TRADE_PAY".getBytes(), charset);
            //付款金额，必填
            total_amount = new String("88.80".getBytes(), charset);
            //
            subject = new String("Iphone6 16G".getBytes(), charset);
            //商品描述，可空
            body = new String("商品描述".getBytes(), charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
        }

        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"" + out_trade_no + "\"," +
                "    \"product_code\":\"" + product_code + "\"," +
                "    \"total_amount\":" + total_amount + "," +
                "    \"subject\":\"" + subject + "\"," +
                "    \"body\":\"" + body + "\"," +
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }" +
                "  }");//填充业务参数

        String form = "";

        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        System.out.println(form);
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        System.out.println(form);

    }
}
