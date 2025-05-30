PGDMP  '                    }        	   ecommerce    17.3    17.3 6    e           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                           false            f           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                           false            g           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                           false            h           1262    17757 	   ecommerce    DATABASE     o   CREATE DATABASE ecommerce WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'it-IT';
    DROP DATABASE ecommerce;
                     postgres    false            �            1255    17849    aggiorna_stock()    FUNCTION     �   CREATE FUNCTION public.aggiorna_stock() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    UPDATE prodotto 
    SET stock = stock - NEW.quantita
    WHERE id_prodotto = NEW.id_prodotto;
    
    RETURN NEW;
END;
$$;
 '   DROP FUNCTION public.aggiorna_stock();
       public               postgres    false            �            1255    17838    update_updated_at_column()    FUNCTION     �   CREATE FUNCTION public.update_updated_at_column() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$;
 1   DROP FUNCTION public.update_updated_at_column();
       public               postgres    false            �            1255    17847    verifica_stock()    FUNCTION     �  CREATE FUNCTION public.verifica_stock() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
    disponibile INTEGER;
BEGIN
    SELECT stock INTO disponibile FROM prodotto WHERE id_prodotto = NEW.id_prodotto;
    
    IF disponibile < NEW.quantita THEN
        RAISE EXCEPTION 'Stock insufficiente per il prodotto ID %: richiesti %, disponibili %', 
            NEW.id_prodotto, NEW.quantita, disponibile;
    END IF;
    
    RETURN NEW;
END;
$$;
 '   DROP FUNCTION public.verifica_stock();
       public               postgres    false            �            1259    17759    cliente    TABLE     �  CREATE TABLE public.cliente (
    id_cliente bigint NOT NULL,
    nome character varying(100) NOT NULL,
    cognome character varying(100) NOT NULL,
    data_nascita date,
    codice_fiscale character varying(16),
    email character varying(255) NOT NULL,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP
);
    DROP TABLE public.cliente;
       public         heap r       postgres    false            �            1259    17758    cliente_id_cliente_seq    SEQUENCE        CREATE SEQUENCE public.cliente_id_cliente_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.cliente_id_cliente_seq;
       public               postgres    false    218            i           0    0    cliente_id_cliente_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.cliente_id_cliente_seq OWNED BY public.cliente.id_cliente;
          public               postgres    false    217            �            1259    17786    ordine    TABLE     �  CREATE TABLE public.ordine (
    id_ordine bigint NOT NULL,
    id_cliente bigint NOT NULL,
    stato character varying(20) DEFAULT 'ORDINATO'::character varying NOT NULL,
    data_creazione timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    data_aggiornamento timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ordine_stato_check CHECK (((stato)::text = ANY ((ARRAY['ORDINATO'::character varying, 'IN_CONSEGNA'::character varying, 'CONSEGNATO'::character varying])::text[])))
);
    DROP TABLE public.ordine;
       public         heap r       postgres    false            �            1259    17785    ordine_id_ordine_seq    SEQUENCE     }   CREATE SEQUENCE public.ordine_id_ordine_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.ordine_id_ordine_seq;
       public               postgres    false    222            j           0    0    ordine_id_ordine_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.ordine_id_ordine_seq OWNED BY public.ordine.id_ordine;
          public               postgres    false    221            �            1259    17807    ordine_item    TABLE       CREATE TABLE public.ordine_item (
    id_ordine_item bigint NOT NULL,
    id_ordine bigint NOT NULL,
    id_prodotto bigint NOT NULL,
    quantita integer NOT NULL,
    prezzo_unitario numeric(10,2) NOT NULL,
    CONSTRAINT ordine_item_quantita_check CHECK ((quantita > 0))
);
    DROP TABLE public.ordine_item;
       public         heap r       postgres    false            �            1259    17806    ordine_item_id_ordine_item_seq    SEQUENCE     �   CREATE SEQUENCE public.ordine_item_id_ordine_item_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 5   DROP SEQUENCE public.ordine_item_id_ordine_item_seq;
       public               postgres    false    224            k           0    0    ordine_item_id_ordine_item_seq    SEQUENCE OWNED BY     a   ALTER SEQUENCE public.ordine_item_id_ordine_item_seq OWNED BY public.ordine_item.id_ordine_item;
          public               postgres    false    223            �            1259    17772    prodotto    TABLE     �  CREATE TABLE public.prodotto (
    id_prodotto bigint NOT NULL,
    codice character varying(50) NOT NULL,
    nome character varying(255) NOT NULL,
    stock integer DEFAULT 0 NOT NULL,
    version integer DEFAULT 0 NOT NULL,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT prodotto_stock_check CHECK ((stock >= 0))
);
    DROP TABLE public.prodotto;
       public         heap r       postgres    false            �            1259    17771    prodotto_id_prodotto_seq    SEQUENCE     �   CREATE SEQUENCE public.prodotto_id_prodotto_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.prodotto_id_prodotto_seq;
       public               postgres    false    220            l           0    0    prodotto_id_prodotto_seq    SEQUENCE OWNED BY     U   ALTER SEQUENCE public.prodotto_id_prodotto_seq OWNED BY public.prodotto.id_prodotto;
          public               postgres    false    219            �            1259    17842    vista_ordini_con_dettagli    VIEW       CREATE VIEW public.vista_ordini_con_dettagli AS
 SELECT o.id_ordine,
    (((c.nome)::text || ' '::text) || (c.cognome)::text) AS cliente,
    o.stato,
    o.data_creazione,
    count(oi.id_ordine_item) AS numero_prodotti,
    sum(((oi.quantita)::numeric * oi.prezzo_unitario)) AS totale
   FROM ((public.ordine o
     JOIN public.cliente c ON ((o.id_cliente = c.id_cliente)))
     LEFT JOIN public.ordine_item oi ON ((o.id_ordine = oi.id_ordine)))
  GROUP BY o.id_ordine, c.nome, c.cognome, o.stato, o.data_creazione;
 ,   DROP VIEW public.vista_ordini_con_dettagli;
       public       v       postgres    false    218    218    218    222    222    222    222    224    224    224    224            �           2604    17762    cliente id_cliente    DEFAULT     x   ALTER TABLE ONLY public.cliente ALTER COLUMN id_cliente SET DEFAULT nextval('public.cliente_id_cliente_seq'::regclass);
 A   ALTER TABLE public.cliente ALTER COLUMN id_cliente DROP DEFAULT;
       public               postgres    false    218    217    218            �           2604    17789    ordine id_ordine    DEFAULT     t   ALTER TABLE ONLY public.ordine ALTER COLUMN id_ordine SET DEFAULT nextval('public.ordine_id_ordine_seq'::regclass);
 ?   ALTER TABLE public.ordine ALTER COLUMN id_ordine DROP DEFAULT;
       public               postgres    false    222    221    222            �           2604    17810    ordine_item id_ordine_item    DEFAULT     �   ALTER TABLE ONLY public.ordine_item ALTER COLUMN id_ordine_item SET DEFAULT nextval('public.ordine_item_id_ordine_item_seq'::regclass);
 I   ALTER TABLE public.ordine_item ALTER COLUMN id_ordine_item DROP DEFAULT;
       public               postgres    false    223    224    224            �           2604    17775    prodotto id_prodotto    DEFAULT     |   ALTER TABLE ONLY public.prodotto ALTER COLUMN id_prodotto SET DEFAULT nextval('public.prodotto_id_prodotto_seq'::regclass);
 C   ALTER TABLE public.prodotto ALTER COLUMN id_prodotto DROP DEFAULT;
       public               postgres    false    220    219    220            \          0    17759    cliente 
   TABLE DATA           y   COPY public.cliente (id_cliente, nome, cognome, data_nascita, codice_fiscale, email, created_at, updated_at) FROM stdin;
    public               postgres    false    218   I       `          0    17786    ordine 
   TABLE DATA           b   COPY public.ordine (id_ordine, id_cliente, stato, data_creazione, data_aggiornamento) FROM stdin;
    public               postgres    false    222   1I       b          0    17807    ordine_item 
   TABLE DATA           h   COPY public.ordine_item (id_ordine_item, id_ordine, id_prodotto, quantita, prezzo_unitario) FROM stdin;
    public               postgres    false    224   NI       ^          0    17772    prodotto 
   TABLE DATA           e   COPY public.prodotto (id_prodotto, codice, nome, stock, version, created_at, updated_at) FROM stdin;
    public               postgres    false    220   kI       m           0    0    cliente_id_cliente_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.cliente_id_cliente_seq', 1, false);
          public               postgres    false    217            n           0    0    ordine_id_ordine_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.ordine_id_ordine_seq', 1, false);
          public               postgres    false    221            o           0    0    ordine_item_id_ordine_item_seq    SEQUENCE SET     M   SELECT pg_catalog.setval('public.ordine_item_id_ordine_item_seq', 1, false);
          public               postgres    false    223            p           0    0    prodotto_id_prodotto_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.prodotto_id_prodotto_seq', 1, false);
          public               postgres    false    219            �           2606    17768 "   cliente cliente_codice_fiscale_key 
   CONSTRAINT     g   ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_codice_fiscale_key UNIQUE (codice_fiscale);
 L   ALTER TABLE ONLY public.cliente DROP CONSTRAINT cliente_codice_fiscale_key;
       public                 postgres    false    218            �           2606    17770    cliente cliente_email_key 
   CONSTRAINT     U   ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_email_key UNIQUE (email);
 C   ALTER TABLE ONLY public.cliente DROP CONSTRAINT cliente_email_key;
       public                 postgres    false    218            �           2606    17766    cliente cliente_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (id_cliente);
 >   ALTER TABLE ONLY public.cliente DROP CONSTRAINT cliente_pkey;
       public                 postgres    false    218            �           2606    17813    ordine_item ordine_item_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.ordine_item
    ADD CONSTRAINT ordine_item_pkey PRIMARY KEY (id_ordine_item);
 F   ALTER TABLE ONLY public.ordine_item DROP CONSTRAINT ordine_item_pkey;
       public                 postgres    false    224            �           2606    17795    ordine ordine_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.ordine
    ADD CONSTRAINT ordine_pkey PRIMARY KEY (id_ordine);
 <   ALTER TABLE ONLY public.ordine DROP CONSTRAINT ordine_pkey;
       public                 postgres    false    222            �           2606    17784    prodotto prodotto_codice_key 
   CONSTRAINT     Y   ALTER TABLE ONLY public.prodotto
    ADD CONSTRAINT prodotto_codice_key UNIQUE (codice);
 F   ALTER TABLE ONLY public.prodotto DROP CONSTRAINT prodotto_codice_key;
       public                 postgres    false    220            �           2606    17782    prodotto prodotto_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.prodotto
    ADD CONSTRAINT prodotto_pkey PRIMARY KEY (id_prodotto);
 @   ALTER TABLE ONLY public.prodotto DROP CONSTRAINT prodotto_pkey;
       public                 postgres    false    220            �           1259    17834    idx_ordine_cliente    INDEX     K   CREATE INDEX idx_ordine_cliente ON public.ordine USING btree (id_cliente);
 &   DROP INDEX public.idx_ordine_cliente;
       public                 postgres    false    222            �           1259    17835    idx_ordine_item_ordine    INDEX     S   CREATE INDEX idx_ordine_item_ordine ON public.ordine_item USING btree (id_ordine);
 *   DROP INDEX public.idx_ordine_item_ordine;
       public                 postgres    false    224            �           1259    17836    idx_ordine_item_prodotto    INDEX     W   CREATE INDEX idx_ordine_item_prodotto ON public.ordine_item USING btree (id_prodotto);
 ,   DROP INDEX public.idx_ordine_item_prodotto;
       public                 postgres    false    224            �           1259    17837    idx_prodotto_codice    INDEX     J   CREATE INDEX idx_prodotto_codice ON public.prodotto USING btree (codice);
 '   DROP INDEX public.idx_prodotto_codice;
       public                 postgres    false    220            �           2620    17850 *   ordine_item tr_aggiorna_stock_after_insert    TRIGGER     �   CREATE TRIGGER tr_aggiorna_stock_after_insert AFTER INSERT ON public.ordine_item FOR EACH ROW EXECUTE FUNCTION public.aggiorna_stock();
 C   DROP TRIGGER tr_aggiorna_stock_after_insert ON public.ordine_item;
       public               postgres    false    228    224            �           2620    17848 +   ordine_item tr_verifica_stock_before_insert    TRIGGER     �   CREATE TRIGGER tr_verifica_stock_before_insert BEFORE INSERT ON public.ordine_item FOR EACH ROW EXECUTE FUNCTION public.verifica_stock();
 D   DROP TRIGGER tr_verifica_stock_before_insert ON public.ordine_item;
       public               postgres    false    224    227            �           2620    17839 !   cliente update_cliente_updated_at    TRIGGER     �   CREATE TRIGGER update_cliente_updated_at BEFORE UPDATE ON public.cliente FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();
 :   DROP TRIGGER update_cliente_updated_at ON public.cliente;
       public               postgres    false    218    226            �           2620    17841    ordine update_ordine_updated_at    TRIGGER     �   CREATE TRIGGER update_ordine_updated_at BEFORE UPDATE ON public.ordine FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();
 8   DROP TRIGGER update_ordine_updated_at ON public.ordine;
       public               postgres    false    226    222            �           2620    17840 #   prodotto update_prodotto_updated_at    TRIGGER     �   CREATE TRIGGER update_prodotto_updated_at BEFORE UPDATE ON public.prodotto FOR EACH ROW EXECUTE FUNCTION public.update_updated_at_column();
 <   DROP TRIGGER update_prodotto_updated_at ON public.prodotto;
       public               postgres    false    220    226            �           2606    17801    ordine fk_cliente    FK CONSTRAINT     }   ALTER TABLE ONLY public.ordine
    ADD CONSTRAINT fk_cliente FOREIGN KEY (id_cliente) REFERENCES public.cliente(id_cliente);
 ;   ALTER TABLE ONLY public.ordine DROP CONSTRAINT fk_cliente;
       public               postgres    false    222    218    4785            �           2606    17824    ordine_item fk_ordine    FK CONSTRAINT     ~   ALTER TABLE ONLY public.ordine_item
    ADD CONSTRAINT fk_ordine FOREIGN KEY (id_ordine) REFERENCES public.ordine(id_ordine);
 ?   ALTER TABLE ONLY public.ordine_item DROP CONSTRAINT fk_ordine;
       public               postgres    false    222    4793    224            �           2606    17829    ordine_item fk_prodotto    FK CONSTRAINT     �   ALTER TABLE ONLY public.ordine_item
    ADD CONSTRAINT fk_prodotto FOREIGN KEY (id_prodotto) REFERENCES public.prodotto(id_prodotto);
 A   ALTER TABLE ONLY public.ordine_item DROP CONSTRAINT fk_prodotto;
       public               postgres    false    4790    224    220            �           2606    17796    ordine ordine_id_cliente_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.ordine
    ADD CONSTRAINT ordine_id_cliente_fkey FOREIGN KEY (id_cliente) REFERENCES public.cliente(id_cliente);
 G   ALTER TABLE ONLY public.ordine DROP CONSTRAINT ordine_id_cliente_fkey;
       public               postgres    false    222    218    4785            �           2606    17814 &   ordine_item ordine_item_id_ordine_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.ordine_item
    ADD CONSTRAINT ordine_item_id_ordine_fkey FOREIGN KEY (id_ordine) REFERENCES public.ordine(id_ordine) ON DELETE CASCADE;
 P   ALTER TABLE ONLY public.ordine_item DROP CONSTRAINT ordine_item_id_ordine_fkey;
       public               postgres    false    4793    222    224            �           2606    17819 (   ordine_item ordine_item_id_prodotto_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.ordine_item
    ADD CONSTRAINT ordine_item_id_prodotto_fkey FOREIGN KEY (id_prodotto) REFERENCES public.prodotto(id_prodotto);
 R   ALTER TABLE ONLY public.ordine_item DROP CONSTRAINT ordine_item_id_prodotto_fkey;
       public               postgres    false    4790    224    220            \      x������ � �      `      x������ � �      b      x������ � �      ^      x������ � �     