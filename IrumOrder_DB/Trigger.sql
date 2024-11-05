USE order_db;

DELIMITER //

CREATE TRIGGER calculate_total_price
BEFORE INSERT ON orders
FOR EACH ROW
BEGIN
    DECLARE base_price INT DEFAULT 0;
    DECLARE option_price INT DEFAULT 0;
    DECLARE menu_id INT;
    DECLARE menuDetail_id INT;

    SELECT menu_id, menuDetail_id INTO menu_id, menuDetail_id
    FROM order_menu
    WHERE order_id = NEW.order_id
    LIMIT 1;

    -- 메뉴 가격 가져오기
    SELECT price INTO base_price FROM menu WHERE menu_id = menu_id;

    IF menuDetail_id IS NOT NULL THEN
        SELECT addShot, useCup, addVanila, addHazelnut INTO @addShot, @useCup, @addVanila, @addHazelnut
        FROM menudetails
        WHERE menuDetail_id = menuDetail_id;

        IF @addShot = TRUE THEN
            SET option_price = option_price + 500;
        END IF;

        IF @useCup = 'TumBler' THEN
            SET option_price = option_price - 200;
        END IF;

        IF @addVanila = TRUE THEN
            SET option_price = option_price + 500;
        END IF;

        IF @addHazelnut = TRUE THEN
            SET option_price = option_price + 500;
        END IF;
    END IF;

    -- 최종 가격 계산
    SET NEW.total_price = base_price + option_price;
END;
//

DELIMITER ;
