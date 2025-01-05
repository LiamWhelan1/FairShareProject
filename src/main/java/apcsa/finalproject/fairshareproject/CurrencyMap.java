package apcsa.finalproject.fairshareproject;

import java.util.Map;

public class CurrencyMap {
    private static final Map<String, String> currencyMap = Map.<String, String>ofEntries(
            Map.entry("AED", "د.إ"), // UAE Dirham
            Map.entry("AFN", "؋"), // Afghan Afghani
            Map.entry("ALL", "Lek"), // Albanian Lek
            Map.entry("AMD", "֏"), // Armenian Dram
            Map.entry("ANG", "ƒ"), // Netherlands Antillean Guilder
            Map.entry("AOA", "Kz"), // Angolan Kwanza
            Map.entry("ARS", "$"), // Argentine Peso
            Map.entry("AUD", "$"), // Australian Dollar
            Map.entry("AWG", "ƒ"), // Aruban Florin
            Map.entry("AZN", "₼"), // Azerbaijani Manat
            Map.entry("BAM", "KM"), // Bosnia-Herzegovina Convertible Mark
            Map.entry("BBD", "$"), // Barbadian Dollar
            Map.entry("BDT", "৳"), // Bangladeshi Taka
            Map.entry("BGN", "лв"), // Bulgarian Lev
            Map.entry("BHD", "ب.د"), // Bahraini Dinar
            Map.entry("BIF", "FBu"), // Burundian Franc
            Map.entry("BMD", "$"), // Bermudian Dollar
            Map.entry("BND", "$"), // Brunei Dollar
            Map.entry("BOB", "Bs."), // Bolivian Boliviano
            Map.entry("BRL", "R$"), // Brazilian Real
            Map.entry("BSD", "$"), // Bahamian Dollar
            Map.entry("BTN", "Nu."), // Bhutanese Ngultrum
            Map.entry("BWP", "P"), // Botswana Pula
            Map.entry("BYN", "Br"), // Belarusian Ruble
            Map.entry("BZD", "$"), // Belize Dollar
            Map.entry("CAD", "$"), // Canadian Dollar
            Map.entry("CDF", "FC"), // Congolese Franc
            Map.entry("CHF", "CHF"), // Swiss Franc
            Map.entry("CLP", "$"), // Chilean Peso
            Map.entry("CNY", "元"), // Chinese Yuan Renminbi
            Map.entry("COP", "$"), // Colombian Peso
            Map.entry("CRC", "₡"), // Costa Rican Colón
            Map.entry("CUC", "$"), // Cuban Convertible Peso
            Map.entry("CUP", "$MN"), // Cuban Peso
            Map.entry("CVE", "Esc"), // Cape Verdean Escudo
            Map.entry("CZK", "Kč"), // Czech Koruna
            Map.entry("DJF", "Fdj"), // Djiboutian Franc
            Map.entry("DKK", "kr"), // Danish Krone
            Map.entry("DOP", "RD$"), // Dominican Peso
            Map.entry("DZD", "د.ج"), // Algerian Dinar
            Map.entry("EGP", "£"), // Egyptian Pound
            Map.entry("ERN", "Nfk"), // Eritrean Nakfa
            Map.entry("ETB", "Br"), // Ethiopian Birr
            Map.entry("EUR", "€"), // Euro
            Map.entry("FJD", "$"), // Fijian Dollar
            Map.entry("FKP", "£"), // Falkland Islands Pound
            Map.entry("FOK", "kr"), // Faroese Króna
            Map.entry("GBP", "£"), // British Pound Sterling
            Map.entry("GEL", "₾"), // Georgian Lari
            Map.entry("GGP", "£"), // Guernsey Pound
            Map.entry("GHS", "₵"), // Ghanaian Cedi
            Map.entry("GIP", "£"), // Gibraltar Pound
            Map.entry("GMD", "D"), // Gambian Dalasi
            Map.entry("GNF", "FG"), // Guinean Franc
            Map.entry("GTQ", "Q"), // Guatemalan Quetzal
            Map.entry("GYD", "$"), // Guyanese Dollar
            Map.entry("HKD", "$"), // Hong Kong Dollar
            Map.entry("HNL", "L"), // Honduran Lempira
            Map.entry("HRK", "kn"), // Croatian Kuna
            Map.entry("HTG", "G"), // Haitian Gourde
            Map.entry("HUF", "Ft"), // Hungarian Forint
            Map.entry("IDR", "Rp"), // Indonesian Rupiah
            Map.entry("ILS", "₪"), // Israeli New Shekel
            Map.entry("IMP", "£"), // Isle of Man Pound
            Map.entry("INR", "₹"), // Indian Rupee
            Map.entry("IQD", "د.ع"), // Iraqi Dinar
            Map.entry("IRR", "﷼"), // Iranian Rial
            Map.entry("ISK", "kr"), // Icelandic Króna
            Map.entry("JMD", "J$"), // Jamaican Dollar
            Map.entry("JOD", "د.ا"), // Jordanian Dinar
            Map.entry("JPY", "¥"), // Japanese Yen
            Map.entry("KES", "Sh"), // Kenyan Shilling
            Map.entry("KGS", "сом"), // Kyrgyzstani Som
            Map.entry("KHR", "៛"), // Cambodian Riel
            Map.entry("KID", "$"), // Kiribati Dollar
            Map.entry("KMF", "CF"), // Comorian Franc
            Map.entry("KRW", "₩"), // South Korean Won
            Map.entry("KWD", "د.ك"), // Kuwaiti Dinar
            Map.entry("KYD", "$"), // Cayman Islands Dollar
            Map.entry("KZT", "₸"), // Kazakhstani Tenge
            Map.entry("LAK", "₭"), // Lao Kip
            Map.entry("LBP", "ل.ل"), // Lebanese Pound
            Map.entry("LKR", "₨"), // Sri Lankan Rupee
            Map.entry("LRD", "$"), // Liberian Dollar
            Map.entry("LSL", "L"), // Lesotho Loti
            Map.entry("LYD", "د.ل"), // Libyan Dinar
            Map.entry("MAD", "د.م."), // Moroccan Dirham
            Map.entry("MDL", "L"), // Moldovan Leu
            Map.entry("MGA", "Ar"), // Malagasy Ariary
            Map.entry("MKD", "ден"), // Macedonian Denar
            Map.entry("MMK", "K"), // Myanmar Kyat
            Map.entry("MNT", "₮"), // Mongolian Tögrög
            Map.entry("MOP", "P"), // Macanese Pataca
            Map.entry("MRU", "UM"), // Mauritanian Ouguiya
            Map.entry("MUR", "₨"), // Mauritian Rupee
            Map.entry("MVR", "Rf"), // Maldivian Rufiyaa
            Map.entry("MWK", "MK"), // Malawian Kwacha
            Map.entry("MXN", "$"), // Mexican Peso
            Map.entry("MYR", "RM"), // Malaysian Ringgit
            Map.entry("MZN", "MT"), // Mozambican Metical
            Map.entry("NAD", "$"), // Namibian Dollar
            Map.entry("NGN", "₦"), // Nigerian Naira
            Map.entry("NIO", "C$"), // Nicaraguan Córdoba
            Map.entry("NOK", "kr"), // Norwegian Krone
            Map.entry("NPR", "₨"), // Nepalese Rupee
            Map.entry("NZD", "$"), // New Zealand Dollar
            Map.entry("OMR", "ر.ع"), // Omani Rial
            Map.entry("PAB", "B/."), // Panamanian Balboa
            Map.entry("PEN", "S/"), // Peruvian Sol
            Map.entry("PGK", "K"), // Papua New Guinean Kina
            Map.entry("PHP", "₱"), // Philippine Peso
            Map.entry("PKR", "₨"), // Pakistani Rupee
            Map.entry("PLN", "zł"), // Polish Złoty
            Map.entry("PYG", "₲"), // Paraguayan Guarani
            Map.entry("QAR", "ر.ق"), // Qatari Riyal
            Map.entry("RON", "lei"), // Romanian Leu
            Map.entry("RSD", "din"), // Serbian Dinar
            Map.entry("RUB", "₽"), // Russian Ruble
            Map.entry("RWF", "FRw"), // Rwandan Franc
            Map.entry("SAR", "ر.س"), // Saudi Riyal
            Map.entry("SBD", "$"), // Solomon Islands Dollar
            Map.entry("SCR", "₨"), // Seychellois Rupee
            Map.entry("SDG", "ج.س."), // Sudanese Pound
            Map.entry("SEK", "kr"), // Swedish Krona
            Map.entry("SGD", "$"), // Singapore Dollar
            Map.entry("SHP", "£"), // Saint Helena Pound
            Map.entry("SLL", "Le"), // Sierra Leonean Leone
            Map.entry("SOS", "Sh"), // Somali Shilling
            Map.entry("SRD", "$"), // Surinamese Dollar
            Map.entry("SSP", "£"), // South Sudanese Pound
            Map.entry("STN", "Db"), // São Tomé and Príncipe Dobra
            Map.entry("SYP", "£"), // Syrian Pound
            Map.entry("SZL", "L"), // Eswatini Lilangeni
            Map.entry("THB", "฿"), // Thai Baht
            Map.entry("TJS", "䁓"), // Tajikistani Somoni
            Map.entry("TMT", "m"), // Turkmenistani Manat
            Map.entry("TND", "د.ت"), // Tunisian Dinar
            Map.entry("TOP", "T$"), // Tongan Paʻanga
            Map.entry("TRY", "₺"), // Turkish Lira
            Map.entry("TTD", "$TT"), // Trinidad and Tobago Dollar
            Map.entry("TVD", "$A"), // Tuvaluan Dollar
            Map.entry("TWD", "NT$"), // New Taiwan Dollar
            Map.entry("TZS", "Sh"), // Tanzanian Shilling
            Map.entry("UAH", "₴"), // Ukrainian Hryvnia
            Map.entry("UGX", "Sh"), // Ugandan Shilling
            Map.entry("USD", "$"), // United States Dollar
            Map.entry("UYU", "$U"), // Uruguayan Peso
            Map.entry("UZS", "лв"), // Uzbekistani So'm
            Map.entry("VES", "Bs.S"), // Venezuelan Bolívar Soberano
            Map.entry("VND", "₫"), // Vietnamese Dong
            Map.entry("VUV", "VT"), // Vanuatu Vatu
            Map.entry("WST", "T"), // Samoan Tālā
            Map.entry("XAF", "FCFA"), // Central African CFA Franc
            Map.entry("XCD", "$"), // East Caribbean Dollar
            Map.entry("XOF", "CFA"), // West African CFA Franc
            Map.entry("XPF", "CFP"), // CFP Franc
            Map.entry("YER", "ر.ي"), // Yemeni Rial
            Map.entry("ZAR", "R"), // South African Rand
            Map.entry("ZMW", "K"), // Zambian Kwacha
            Map.entry("ZWL", "$Z") // Zimbabwean Dollar
    );

    public static Map<String, String> getCurrencyMap() {
        return currencyMap;
    }
}
