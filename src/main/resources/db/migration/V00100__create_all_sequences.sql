--  ツアー担当者コードの生成用シーケンス  --
CREATE SEQUENCE TOUR_CON_CODE_SEQ
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9999999999
    START WITH 1
    NO CYCLE
;

--  顧客コードの生成用シーケンス  --
CREATE SEQUENCE CUSTOMER_CODE_SEQ
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 99999999
    START WITH 1
    NO CYCLE
;

--  予約番号の生成用シーケンス  --
CREATE SEQUENCE RESERVE_NO_SEQ
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 99999999
    START WITH 1
    NO CYCLE
;

COMMIT;