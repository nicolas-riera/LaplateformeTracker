--
-- PostgreSQL database dump
--

\restrict j70cCTxg3EnKbIev8YPlk38Xaw5Q3OfAiQBm2AbJ9s6cxS5ZeldoM999ybPABML

-- Dumped from database version 18.3
-- Dumped by pg_dump version 18.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: grade; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.grade (
    id integer NOT NULL,
    student_id integer NOT NULL,
    date date DEFAULT CURRENT_DATE NOT NULL,
    skill character varying(100) NOT NULL,
    grade double precision NOT NULL
);


ALTER TABLE public.grade OWNER TO postgres;

--
-- Name: grade_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.grade_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.grade_id_seq OWNER TO postgres;

--
-- Name: grade_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.grade_id_seq OWNED BY public.grade.id;


--
-- Name: manager; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.manager (
    id integer NOT NULL,
    email character varying(254) NOT NULL,
    password_hash character varying(60),
    first_name character varying(50) CONSTRAINT manager_firstname_not_null NOT NULL,
    last_name character varying(50) CONSTRAINT manager_lastname_not_null NOT NULL
);


ALTER TABLE public.manager OWNER TO postgres;

--
-- Name: manager_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.manager_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.manager_id_seq OWNER TO postgres;

--
-- Name: manager_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.manager_id_seq OWNED BY public.manager.id;


--
-- Name: student; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.student (
    id integer NOT NULL,
    manager_id integer,
    email character varying(254) NOT NULL,
    password_hash character varying(60),
    first_name character varying(50) CONSTRAINT student_firstname_not_null NOT NULL,
    last_name character varying(50) CONSTRAINT student_lastname_not_null NOT NULL,
    date_of_birth date NOT NULL,
    address character varying(150) NOT NULL,
    phone character varying(20) NOT NULL,
    is_deleted boolean DEFAULT false,
    degree character varying(2) NOT NULL,
    CONSTRAINT check_degree_valid CHECK (((degree)::text = ANY ((ARRAY['1J'::character varying, '1L'::character varying, '1D'::character varying, '1C'::character varying, '2J'::character varying, '2L'::character varying, '2D'::character varying, '2C'::character varying, '3J'::character varying, '3L'::character varying, '3D'::character varying, '3C'::character varying, '4J'::character varying, '4L'::character varying, '4D'::character varying, '4C'::character varying, '5J'::character varying, '5L'::character varying, '5D'::character varying, '5C'::character varying])::text[])))
);


ALTER TABLE public.student OWNER TO postgres;

--
-- Name: student_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.student_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.student_id_seq OWNER TO postgres;

--
-- Name: student_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.student_id_seq OWNED BY public.student.id;


--
-- Name: grade id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.grade ALTER COLUMN id SET DEFAULT nextval('public.grade_id_seq'::regclass);


--
-- Name: manager id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.manager ALTER COLUMN id SET DEFAULT nextval('public.manager_id_seq'::regclass);


--
-- Name: student id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.student ALTER COLUMN id SET DEFAULT nextval('public.student_id_seq'::regclass);


--
-- Data for Name: grade; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.grade (id, student_id, date, skill, grade) FROM stdin;
1	11	2026-04-10	Java Core	12.5
2	11	2026-04-12	SQL	14
3	12	2026-04-10	Java Core	16
4	12	2026-04-12	SQL	17.5
5	12	2026-04-15	Algorithm	15
6	13	2026-04-10	Java Core	9
7	13	2026-04-12	SQL	11
8	14	2026-04-10	Java Core	15.5
9	14	2026-04-13	UI/UX	18
10	15	2026-04-10	Java Core	13
11	15	2026-04-12	SQL	12
12	15	2026-04-14	Git	14.5
13	16	2026-04-10	Java Core	19
14	16	2026-04-12	SQL	18.5
15	17	2026-04-10	Java Core	11
16	17	2026-04-16	Network	10.5
17	18	2026-04-10	Java Core	14.5
18	18	2026-04-12	SQL	16
19	18	2026-04-20	Python	15
20	19	2026-04-10	Java Core	8.5
21	19	2026-04-12	SQL	7
22	20	2026-04-10	Java Core	17
23	20	2026-04-12	SQL	15.5
24	20	2026-04-18	Docker	14
25	1	2026-04-01	Java Core	15
26	1	2026-04-03	SQL	12.5
27	2	2026-04-01	Java Core	18.5
28	2	2026-04-03	SQL	19
29	2	2026-04-05	Git	17
30	3	2026-04-01	Java Core	9.5
31	3	2026-04-03	SQL	11
32	4	2026-04-01	Java Core	14
33	4	2026-04-04	UI/UX	16.5
34	5	2026-04-01	Java Core	12
35	5	2026-04-03	SQL	10.5
36	5	2026-04-06	Network	13
37	6	2026-04-01	Java Core	16
38	6	2026-04-03	SQL	15.5
39	7	2026-04-01	Java Core	8
40	7	2026-04-07	Algorithm	9.5
41	8	2026-04-01	Java Core	13.5
42	8	2026-04-03	SQL	14
43	8	2026-04-08	Soft Skills	18
44	9	2026-04-01	Java Core	11.5
45	9	2026-04-03	SQL	13
46	10	2026-04-01	Java Core	17.5
47	10	2026-04-03	SQL	16
48	10	2026-04-09	API REST	15
\.


--
-- Data for Name: manager; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.manager (id, email, password_hash, first_name, last_name) FROM stdin;
1	manager@laplateforme.io	\N	Directeur	Plateforme
\.


--
-- Data for Name: student; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.student (id, manager_id, email, password_hash, first_name, last_name, date_of_birth, address, phone, is_deleted, degree) FROM stdin;
1	\N	thomas.morel@laplateforme.io	\N	Thomas	Morel	2001-05-12	20 rue de la République, Marseille	0600000001	f	1J
2	\N	julie.rousseau@laplateforme.io	\N	Julie	Rousseau	2002-11-03	5 bd de Dunkerque, Marseille	0600000002	f	1L
3	\N	mathieu.gauthier@laplateforme.io	\N	Mathieu	Gauthier	2000-02-25	12 rue Sainte, Marseille	0600000003	f	1D
4	\N	sarah.andre@laplateforme.io	\N	Sarah	Andre	2003-08-14	88 rue d'Aubagne, Marseille	0600000004	f	1C
5	\N	nicolas.masson@laplateforme.io	\N	Nicolas	Masson	1999-12-30	3 place de la Joliette, Marseille	0600000005	f	2J
6	\N	lea.marchand@laplateforme.io	\N	Léa	Marchand	2002-04-18	15 rue de Rome, Marseille	0600000006	f	2L
7	\N	kevin.barbier@laplateforme.io	\N	Kevin	Barbier	2001-07-07	40 rue Breteuil, Marseille	0600000007	f	2D
8	\N	camille.fontaine@laplateforme.io	\N	Camille	Fontaine	2000-10-22	22 rue d'Endoume, Marseille	0600000008	f	2C
9	\N	alexandre.guillot@laplateforme.io	\N	Alexandre	Guillot	2002-01-15	11 bd National, Marseille	0600000009	f	3J
10	\N	manon.brunet@laplateforme.io	\N	Manon	Brunet	2003-06-21	7 rue de Lodi, Marseille	0600000010	f	3L
11	\N	hugo.faure@laplateforme.io	\N	Hugo	Faure	2001-02-10	14 bd Sakakini, Marseille	0600000011	f	3D
12	\N	ines.mercier@laplateforme.io	\N	Inès	Mercier	2002-09-28	33 rue Saint-Ferréol, Marseille	0600000012	f	3C
13	\N	arthur.blanc@laplateforme.io	\N	Arthur	Blanc	2000-05-05	50 av du Prado, Marseille	0600000013	f	1J
14	\N	louna.guerin@laplateforme.io	\N	Louna	Guerin	2003-12-01	8 rue de la Loge, Marseille	0600000014	f	1L
15	\N	jules.boyer@laplateforme.io	\N	Jules	Boyer	2001-08-19	120 rue de Rome, Marseille	0600000015	f	1D
16	\N	alice.chevalier@laplateforme.io	\N	Alice	Chevalier	2002-03-25	21 rue d'Aix, Marseille	0600000016	f	1C
17	\N	maxime.lucas@laplateforme.io	\N	Maxime	Lucas	1999-10-14	9 rue de l'Évêché, Marseille	0600000017	f	2J
18	\N	jade.garcia@laplateforme.io	\N	Jade	Garcia	2003-04-02	5 av de la Capelette, Marseille	0600000018	f	2L
19	\N	quentin.roux@laplateforme.io	\N	Quentin	Roux	2000-11-30	18 rue de Lodi, Marseille	0600000019	f	2D
20	\N	clara.moreau@laplateforme.io	\N	Clara	Moreau	2001-06-15	44 bd Chave, Marseille	0600000020	f	3C
\.


--
-- Name: grade_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.grade_id_seq', 48, true);


--
-- Name: manager_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.manager_id_seq', 1, true);


--
-- Name: student_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.student_id_seq', 20, true);


--
-- Name: grade grade_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.grade
    ADD CONSTRAINT grade_pkey PRIMARY KEY (id);


--
-- Name: manager manager_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.manager
    ADD CONSTRAINT manager_pkey PRIMARY KEY (id);


--
-- Name: student student_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.student
    ADD CONSTRAINT student_pkey PRIMARY KEY (id);


--
-- Name: grade grade_student_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.grade
    ADD CONSTRAINT grade_student_id_fkey FOREIGN KEY (student_id) REFERENCES public.student(id);


--
-- Name: student student_manager_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.student
    ADD CONSTRAINT student_manager_id_fkey FOREIGN KEY (manager_id) REFERENCES public.manager(id);


--
-- PostgreSQL database dump complete
--

\unrestrict j70cCTxg3EnKbIev8YPlk38Xaw5Q3OfAiQBm2AbJ9s6cxS5ZeldoM999ybPABML

