package com.dpp.entity;

import com.dpp.storage.StorageException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;

@Data
@NoArgsConstructor
@Entity
public class OrderDtoResponse {

  private static final List<String> proceduresType = Arrays
      .asList("Двухэтапный конкурс",
          "Закрытый аукцион",
          "Закрытый запрос котировок",
          "Закрытый запрос предложений",
          "Закрытый конкурс",
          "Закрытый конкурс с ограниченным участием",
          "Закупка у единственного поставщика",
          "Запрос котировок",
          "Запрос предложений",
          "Конкурс с ограниченным участием",
          "Открытый конкурс",
          "Предварительный отбор",
          "Электронный аукцион");
  private static final List<String> listOfRequirements = Arrays
      .asList("Закупка только СМП или СОНКО",
          "Есть единые требования к участникам",
          "Допустимо привлечение субподрядчиков из СМП или СОНКО",
          "Отсутствие в РНП",
          "Опыт работы и деловая репутация",
          "Наличие квалифицированных специалистов",
          "Наличие финансовых ресурсов",
          "Право собственности");

  private static final List<String> fzList = Arrays.asList("223", "44", "94");

  @Id
  @JsonProperty("product_code")
  private String code;

  @JsonProperty("product")
  @Column(length = 100000)
  @Lob
  private String product;

  @JsonProperty("region")
  private Integer region;

  @JsonProperty("initial_price")
  /** The initial price. */
  private Double initialPrice;

  @JsonProperty("final_price")
  /** The final price. */
  private Double finalPrice;

  @JsonProperty("purchase_code")
  private Integer purchaseCode;

  @JsonProperty("law_code")
  private Integer lawCode;

  @JsonProperty("requirements")
  private Integer requirements;

  @JsonProperty("inn")
  private Integer inn;

  public static Builder newBuilder() {
    return new OrderDtoResponse().new Builder();
  }

  public class Builder {

    private Builder() {
    }

    public Builder setCode(String code) {
      if (Strings.isNullOrEmpty(code) || code.equalsIgnoreCase("Код")) {
        throw new StorageException("Code is not valid");
      }
      OrderDtoResponse.this.code = code;
      return this;
    }

    public Builder setInn(String inn) {
      OrderDtoResponse.this.inn = Integer.valueOf(inn);
      return this;
    }

    public Builder setRequirements(String requirements) {
      if (requirements != null) {
        final List<ExtractedResult> search = FuzzySearch
            .extractTop(requirements, listOfRequirements, 1);
        if (search.get(0).getScore() >= 90) {
          OrderDtoResponse.this.requirements = search.get(0).getIndex() + 1;
        } else {
          OrderDtoResponse.this.requirements = 0;
        }
      }
      return this;
    }

    public Builder setProduct(String objectOfOrder) {
      OrderDtoResponse.this.product = objectOfOrder;
      return this;
    }

    public Builder withLawCode(String fz) {

      if (fz != null) {
        final List<ExtractedResult> search = FuzzySearch
            .extractTop(fz, fzList, 1);
        if (search.get(0).getScore() >= 90) {
          OrderDtoResponse.this.lawCode = search.get(0).getIndex() + 1;
        } else {
          OrderDtoResponse.this.lawCode = 0;
        }
      }
      return this;
    }

    public Builder setRegion(String inn) {
      try {
        OrderDtoResponse.this.region = Integer.valueOf(inn.trim().substring(0, 2));
      } catch (Exception e) {
        OrderDtoResponse.this.region = 0;
      }

      return this;
    }

    public Builder setInitialPrice(String initialPrice) {
      String number = initialPrice.replaceAll("[,]", ".").replaceAll("[^0-9|.]", "").trim();
      OrderDtoResponse.this.initialPrice = Double.parseDouble(number.replaceAll("\\s+", ""));
      return this;
    }

    public Builder setFinalPrice(String tradeResults, String objectOfTrade) {
      if (objectOfTrade != null && tradeResults != null) {
        String converted = objectOfTrade.replaceAll("(\\u007C)\\1", " | | ");
        String[] arrayTrades = converted.split(Pattern.quote("|"));
        String[] prices = tradeResults.split(Pattern.quote("|"));

        for (int i = 0; i < arrayTrades.length; i++) {
          if (arrayTrades[i].trim().equalsIgnoreCase("Победитель")) {
            OrderDtoResponse.this.finalPrice = Double
                .parseDouble(prices[i].replace(",", ".").replaceAll("\\s+", ""));
            break;
          }
        }
      }
      return this;
    }

    public Builder withPurchaseCode(String procedureType) {
      if (procedureType != null) {
        final List<ExtractedResult> search = FuzzySearch
            .extractTop(procedureType, proceduresType, 1);
        if (search.get(0).getScore() >= 90) {
          OrderDtoResponse.this.purchaseCode = search.get(0).getIndex() + 1;
        } else {
          OrderDtoResponse.this.purchaseCode = 0;
        }
      }
      return this;
    }

    public OrderDtoResponse build() {
      return OrderDtoResponse.this;
    }


  }

  public OrderDtoResponse(String code, String product, String region, String initialPrice,
      String finalPrice, String purchaseCode, String lawCode, String requirements, String inn) {
  }
}
