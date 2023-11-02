package com.parser.parser.data.entity;

import com.parser.parser.dto.response.Item;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "stickers")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stickers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    @Column(name = "short_name")
    private String shortName;
    @Column(name = "global_item_id")
    private Long globalItemId;
    @Column(name = "sell_min_price")
    private Double sellMinPrice;
    @Column(name = "steam_market_url")
    private String steamMarketUrl;
    @Column(name = "icon_url")
    private String iconUrl;
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public static Stickers convert(Item item) {
        return Stickers.builder()
                .shortName(item.getShortName())
                .globalItemId((long) item.getId())
                .steamMarketUrl(item.getSteamMarketUrl())
                .iconUrl(item.getGoodsInfo().getIconUrl())
                .sellMinPrice(Double.valueOf(item.getSellMinPrice()))
                .build();
    }

}
