 create extension if not exists "uuid-ossp";
 select uuid_generate_v3(uuid_nil(), 'abc');