package com.examination3.address.domain.exception;

import static com.examination3.address.domain.exception.ExceptionMessage.UNEXPECTED_ERROR_MESSAGE_FOR_LOG;
import static com.examination3.address.domain.exception.ExceptionMessage.UNEXPECTED_ERROR_MESSAGE_FOR_RESPONSE;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 住所情報の例外をグローバルに処理するクラス。 アプリケーション全体で発生する特定の例外に対して、共通のレスポンス形式を提供します。
 */
@Slf4j
@ControllerAdvice
public class AddressGlobalExceptionHandler {
    private static final String KEY_OF_CODE = "code";
    private static final String KEY_OF_MESSAGE = "message";
    private static final String KEY_OF_DETAILS = "details";

    /**
     * {@link AddressNotFoundException}が発生した場合の処理を行います。 クライアントにはBadRequestと共にエラー情報を返します。
     *
     * @param e 発生した{@link AddressNotFoundException}
     * @return エラー情報を含むレスポンスエンティティ
     */
    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleAddressNotFoundException(
        AddressNotFoundException e
    ) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(KEY_OF_CODE, "0003");
        body.put(KEY_OF_MESSAGE, e.getMessage());
        body.put(KEY_OF_DETAILS, new ArrayList<>());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * 入力値が不正の場合の例外（{@link IllegalArgumentException}）を処理します。 バリデーションエラーの詳細をクライアントに返します。
     *
     * @param e 発生した{@link IllegalArgumentException}
     * @return バリデーションエラー情報を含むレスポンスエンティティ。
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(
        IllegalArgumentException e
    ) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(KEY_OF_CODE, "0002");
        body.put(KEY_OF_MESSAGE, "request validation error is occurred.");
        List<String> details = List.of(e.getMessage());
        body.put(KEY_OF_DETAILS, details);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * {@link DataAccessException}が発生した場合の処理を行います。 クライアントにはInternalServerErrorと共にエラー情報を返します。
     *
     * @param e 発生した{@link DataAccessException}
     * @return エラー情報を含むレスポンスエンティティ。
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Map<String, Object>> handleDataAccessException(
        DataAccessException e
    ) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(KEY_OF_CODE, "0001");
        body.put(KEY_OF_MESSAGE, "A data access error occurred.");

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 予期しない例外を捕捉する汎用のエラーハンドラ
     *
     * @param e 発生した例外
     * @return エラー情報を含むレスポンスエンティティ
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnexpectedExceptions(Exception e) {
        log.error(UNEXPECTED_ERROR_MESSAGE_FOR_LOG.getMessage());
        log.debug("Detail: " + e.getMessage());

        Map<String, Object> body = new LinkedHashMap<>();
        body.put(KEY_OF_CODE, "9999");
        body.put(KEY_OF_MESSAGE, UNEXPECTED_ERROR_MESSAGE_FOR_RESPONSE.getMessage());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
