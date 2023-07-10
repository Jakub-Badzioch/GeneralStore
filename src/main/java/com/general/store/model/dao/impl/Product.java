package com.general.store.model.dao.impl;

import com.general.store.model.dao.Basic;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends Basic implements IdentifiedDataSerializable {
    private String name;
    private BigDecimal price;
    private String filePath;
    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Comment> comments;
    private String description;
    private Long quantity;
    private Integer scoreCount;
    private Double score;

    // struktury, strumienie, wszystko co w javie. ogolnie mowiac wszystko

    /// w cacheowaniu chodzi o to zeby odczyt byl przedewszystkim najszybszy
    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        out.writeLong(getId());
        out.writeString(getCreatedDate().format(formatter));
        out.writeString(getLastModifiedDate().format(formatter));
        out.writeString(getCreatedBy());
        out.writeString(getLastModifiedBy());
        out.writeLong(getVersion());
        out.writeString(name);
        out.writeString(price.toString());
        out.writeString(filePath);


        out.writeInt(comments != null ? comments.size() : 0);
        for (Comment comment : comments) {
            out.writeObject(comment);
        }

        out.writeString(description);
        out.writeLong(quantity);
        out.writeInt(scoreCount);
        out.writeDouble(score);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        setId(in.readLong());
        setCreatedDate(LocalDateTime.parse(Objects.requireNonNull(in.readString()), formatter));
        setLastModifiedDate(LocalDateTime.parse(Objects.requireNonNull(in.readString()), formatter));
        setCreatedBy(in.readString());
        setLastModifiedBy(in.readString());
        setVersion(in.readLong());
        name = in.readString();
        price = BigDecimal.valueOf(Double.parseDouble(Objects.requireNonNull(in.readString())));
        filePath = in.readString();
        final int size = in.readInt();
        // jesli to nie zadziala to wgl nie dodawaj komentow do cachea. zaciagnij je potem w osobnym endpoitncie
        // selektem z bazy. najpierw produkt potem komentarze w tle jak user juz czyta strone
        final List<Comment> comments = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            comments.add(in.readObject());
        }
        this.comments = comments;
        description = in.readString();
        quantity = in.readLong();
        scoreCount = in.readInt();
        score = in.readDouble();
    }

    // zaczynam od jakiejs wartosci wiekszej od 0. i podbijam o 1 kazda nastepne factoryid w mapie w hazelcastcie
    @Override
    public int getFactoryId() {
        return 1;
    }

    @Override
    public int getClassId() {
        return 1;
    }
}