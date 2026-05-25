INSERT INTO game_item (title, platform, genre, sku_code, description, price, image_url, is_deleted)
SELECT 'Super Mario World', 'SNES', 'Plataforma', 'RET-1001', 'Classico absoluto da era 16-bit.', 199.90, '/img/retro-1.svg', null
WHERE NOT EXISTS (SELECT 1 FROM game_item WHERE sku_code = 'RET-1001');

INSERT INTO game_item (title, platform, genre, sku_code, description, price, image_url, is_deleted)
SELECT 'Sonic The Hedgehog', 'Mega Drive', 'Plataforma', 'RET-1002', 'Velocidade e trilha sonora iconica.', 179.90, '/img/retro-2.svg', null
WHERE NOT EXISTS (SELECT 1 FROM game_item WHERE sku_code = 'RET-1002');

INSERT INTO game_item (title, platform, genre, sku_code, description, price, image_url, is_deleted)
SELECT 'The King of Fighters 98', 'Arcade', 'Luta', 'RET-1003', 'Combates classicos de fliperama.', 149.90, '/img/retro-3.svg', null
WHERE NOT EXISTS (SELECT 1 FROM game_item WHERE sku_code = 'RET-1003');

INSERT INTO game_item (title, platform, genre, sku_code, description, price, image_url, is_deleted)
SELECT 'Top Gear', 'SNES', 'Corrida', 'RET-1004', 'Corrida retro com multiplayer local.', 129.90, '/img/retro-4.svg', null
WHERE NOT EXISTS (SELECT 1 FROM game_item WHERE sku_code = 'RET-1004');
