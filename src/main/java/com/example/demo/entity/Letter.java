package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
@Table(name = "Letter")
public class Letter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Letter")
    private Integer idLetter;

    @Column(name = "Letter_Code", nullable = false, length = 100, unique = true)
    private String LetterCode;

    @Column(name = "LetterTitle", length = 100, unique = true)
    private String LetterTitle;

    @Column(name = "Place_Sending", length = 100)
    private String placeSending;

    @Column(name = "Signer_Letter", length = 200)
    private String signerLetter;

    @Column(name = "Infomartion_UserLetter", length = 200)
    private String infomartionUserLetter;

    @Column(name = "Date_Issue")
    private LocalDate dateIssue;

    @Column(name = "Day_Signer")
    private LocalDate daySigner;

    @Column(name = "Date_Effective")
    private LocalDate dateEffective;

    @Column(name = "Abstracts", columnDefinition = "TEXT")
    private String abstracts;

    @Column(name = "Deadline", length = 100)
    private String deadline;

    @Column(name = "Prioritize", nullable = false, length = 100)
    @Convert(converter = DispatchPriorityConverter.class)
    private DispatchPriority prioritize;

    @Column(name = "LetterType", nullable = false, length = 100)
    @Convert(converter = LetterTypeConverter.class)
    private LetterType letterType;

    @Column(name = "LetterSercurity", nullable = false, length = 100)
    @Convert(converter = LetterSercurityConverter.class)
    private LetterSercurity letterSercurity;
    
    @Column(name = "Id_User", nullable = false)
    private Integer idUser;

    @Column(name = "CreationDate", nullable = false)
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "Id_User", insertable = false, updatable = false)
    private Users user;

    @OneToMany(mappedBy = "letter", fetch = FetchType.EAGER)
    private List<FilesSave> files;

    @OneToMany(mappedBy = "letter", fetch = FetchType.EAGER)
    private List<LetterProcessing> letterProcessings;

    @OneToMany(mappedBy = "letter", fetch = FetchType.EAGER)
    private List<LetterAssign> letterAssigns;

    @OneToMany(mappedBy = "fromLetter", fetch = FetchType.EAGER)
    private List<LetterRelation> letterRelationsFrom;

    @OneToMany(mappedBy = "toLetter", fetch = FetchType.EAGER)
    private List<LetterRelation> letterRelationsTo;

    
    @OneToMany(mappedBy = "letter", fetch = FetchType.EAGER)
    private List<LetterDepartment> letterDepartments;

    public enum LetterSercurity {
        CAO("Cao"),
        THAP("Thấp");   
   
       private final String value;
   
       LetterSercurity(String value) {
           this.value = value;
       }
   
       public String getValue() {
           return value;
       }

       public static LetterSercurity fromValue(String value) {
           for (LetterSercurity status : LetterSercurity.values()) {
               if (status.getValue().equals(value)) {
                   return status;
               }
           }
           throw new IllegalArgumentException("Unknown value: " + value);
       }
   }
   
   @Converter
   public static class LetterSercurityConverter implements AttributeConverter<LetterSercurity, String> {
       @Override
       public String convertToDatabaseColumn(LetterSercurity attribute) {
           return attribute == null ? null : attribute.getValue();
       }
   
       @Override
       public LetterSercurity convertToEntityAttribute(String dbData) {
           if (dbData == null) {
               return null;
           }
           
           for (LetterSercurity status : LetterSercurity.values()) {
               if (status.getValue().equals(dbData)) {
                   return status;
               }
           }
           throw new IllegalArgumentException("Unknown database value: " + dbData);
       }
   }

    
    


    public enum LetterType {
        DEN("Đến"),
        DI("Đi");   
   
       private final String value;
   
       LetterType(String value) {
           this.value = value;
       }
   
       public String getValue() {
           return value;
       }

       public static LetterType fromValue(String value) {
           for (LetterType status : LetterType.values()) {
               if (status.getValue().equals(value)) {
                   return status;
               }
           }
           throw new IllegalArgumentException("Unknown value: " + value);
       }
   }
   
   @Converter
   public static class LetterTypeConverter implements AttributeConverter<LetterType, String> {
       @Override
       public String convertToDatabaseColumn(LetterType attribute) {
           return attribute == null ? null : attribute.getValue();
       }
   
       @Override
       public LetterType convertToEntityAttribute(String dbData) {
           if (dbData == null) {
               return null;
           }
           
           for (LetterType status : LetterType.values()) {
               if (status.getValue().equals(dbData)) {
                   return status;
               }
           }
           throw new IllegalArgumentException("Unknown database value: " + dbData);
       }
   }


    public enum DispatchPriority {
         CAO("Cao"),
         THAP("Thấp"),
         TRUNG_BINH("Trung bình");       
    
        private final String value;
    
        DispatchPriority(String value) {
            this.value = value;
        }
    
        public String getValue() {
            return value;
        }

        public static DispatchPriority fromValue(String value) {
            for (DispatchPriority status : DispatchPriority.values()) {
                if (status.getValue().equals(value)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Unknown value: " + value);
        }
    }
    
    @Converter
    public static class DispatchPriorityConverter implements AttributeConverter<DispatchPriority, String> {
        @Override
        public String convertToDatabaseColumn(DispatchPriority attribute) {
            return attribute == null ? null : attribute.getValue();
        }
    
        @Override
        public DispatchPriority convertToEntityAttribute(String dbData) {
            if (dbData == null) {
                return null;
            }
            
            for (DispatchPriority status : DispatchPriority.values()) {
                if (status.getValue().equals(dbData)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Unknown database value: " + dbData);
        }
    }
} 
