package com.dpp.entity;

import com.dpp.storage.StorageException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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

  private static final List<String> addresses = Arrays.asList("Республика Адыгея",
      "Республика Башкортостан",
      "Республика Бурятия",
      "Республика Алтай",
      "Республика Дагестан",
      "Республика Ингушетия",
      "Кабардино-Балкарская Республика",
      "Республика Калмыкия",
      "Республика Карачаево-Черкесия",
      "Республика Карелия",
      "Республика Коми",
      "Республика Марий Эл",
      "Республика Мордовия",
      "Республика Саха (Якутия)",
      "Республика Северная Осетия — Алания",
      "Республика Татарстан",
      "Республика Тыва",
      "Удмуртская Республика",
      "Республика Хакасия",
      "Чувашская Республика",
      "Алтайский край",
      "Краснодарский край",
      "Красноярский край",
      "Приморский край",
      "Ставропольский край",
      "Хабаровский край",
      "Амурская область",
      "Архангельская область",
      "Астраханская область",
      "Белгородская область",
      "Брянская область",
      "Владимирская область",
      "Волгоградская область",
      "Вологодская область",
      "Воронежская область",
      "Ивановская область",
      "Иркутская область",
      "Калининградская область",
      "Калужская область",
      "Камчатский край",
      "Кемеровская область",
      "Кировская область",
      "Костромская область",
      "Курганская область",
      "Курская область",
      "Ленинградская область",
      "Липецкая область",
      "Магаданская область",
      "Московская область",
      "Мурманская область",
      "Нижегородская область",
      "Новгородская область",
      "Новосибирская область",
      "Омская область",
      "Оренбургская область",
      "Орловская область",
      "Пензенская область",
      "Пермский край",
      "Псковская область",
      "Ростовская область",
      "Рязанская область",
      "Самарская область",
      "Саратовская область",
      "Сахалинская область",
      "Свердловская область",
      "Смоленская область",
      "Тамбовская область",
      "Тверская область",
      "Томская область",
      "Тульская область",
      "Тюменская область",
      "Ульяновская область",
      "Челябинская область",
      "Забайкальский край",
      "Ярославская область",
      "Москва",
      "Санкт-Петербург",
      "Еврейская автономная область",
      "Республика Крым",
      "Ненецкий автономный округ",
      "Ханты-Мансийский автономный округ Югра",
      "Чукотский автономный округ",
      "Ямало-Ненецкий автономный округ",
      "Севастополь",
      "Байконур",
      "Чеченская республика");

  private static final List<Integer> codeRegions = Arrays.asList(
      1,
      2,
      3,
      4,
      5,
      6,
      7,
      8,
      9,
      10,
      11,
      12,
      13,
      14,
      15,
      16,
      17,
      18,
      19,
      21,
      22,
      23,
      24,
      25,
      26,
      27,
      28,
      29,
      30,
      31,
      32,
      33,
      34,
      35,
      36,
      37,
      38,
      39,
      40,
      41,
      42,
      43,
      44,
      45,
      46,
      47,
      48,
      49,
      50,
      51,
      52,
      53,
      54,
      55,
      56,
      57,
      58,
      59,
      60,
      61,
      62,
      63,
      64,
      65,
      66,
      67,
      68,
      69,
      70,
      71,
      72,
      73,
      74,
      75,
      76,
      77,
      78,
      79,
      82,
      83,
      86,
      87,
      89,
      92,
      94,
      95);


  private static final List<String> fzList = Arrays.asList("223", "44", "94");

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Integer code;

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
  private BigInteger inn;

  public static Builder newBuilder() {
    return new OrderDtoResponse().new Builder();
  }

  public class Builder {

    private Builder() {
    }


    public Builder setInn(String inn) {
      OrderDtoResponse.this.inn = BigInteger.valueOf(Long.valueOf(inn));
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

    private Integer getKey(ExtractedResult extractedResult) {
      Map<String, Integer> map = new HashMap<>();
      for (int i = 0; i < addresses.size(); i++) {
        map.put(addresses.get(i), codeRegions.get(i));
      }
      return map.get(extractedResult.getString());
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

    public Builder setRegion(String inn, String region) {
      try {
        OrderDtoResponse.this.region = Integer.valueOf(inn.trim().substring(0, 2));
      } catch (Exception e) {
        final List<ExtractedResult> search = FuzzySearch
            .extractTop(region, addresses, 1);
        if (search.get(0).getScore() >= 90) {
          OrderDtoResponse.this.region = getKey(search.get(0));
        } else {
          OrderDtoResponse.this.region = 0;
        }
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
