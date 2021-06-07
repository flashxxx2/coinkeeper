// just for demo, но показывает, каким сложным можно сделать обработку запроса
function authToken(r) {
  // r -> request, response и ещё делать подзапросы
  r.log(JSON.stringify(r)); // log -> info
  // /_auth_token_request берётся из nginx.conf (т.е. туда отправляется запрос)
  // reply -> {} - callback по завершению запроса на auth service
  r.subrequest("/_auth_token_request", reply => {
    // sample по обработке ответа
    if (reply.status < 200 || reply.status > 299) {
      r.error("unknown response (HTTP " + reply.status + "). " + reply.body);
      r.return(401);
    }

    try {
      r.log("token response: " + reply.responseBody); // GatewayController ResponseEntity.ok().body(...);
      var response = JSON.parse(reply.responseBody);
      // вместо ок можно передавать код ошибки
      if (response.ok === true) {
        r.warn("token is ok");
        // "корявая история" ушла сюда -> r.headersOut
        // пишем ответ тому, кто вызвал auth_request
        r.headersOut['X-Profile'] = reply.responseBody.toString('base64');
        r.status = 200;
        r.sendHeader();
        r.finish();
        return;
      }
      r.warn("token not ok");
      r.return(403);
    } catch (e) {
      r.error(e);
      r.error("error with token: " + reply.body);
      r.return(401);
    }
  });
  r.return(401);
}

export default {authToken}