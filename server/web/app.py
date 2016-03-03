import json
import time
import logging
from flask import Flask
from flask import request
from flask import abort
from redis import Redis

app = Flask(__name__)
redis = Redis(host="redis_1", port=6379)


def delete_stale_games():
    games = redis.hgetall("games")
    for i in games:
        game = json.loads(games[i])
        if (time.time() - game["start"] > 1000):
            redis.hdel("games", i)

@app.route("/game/create/")
def create():
    game_id = redis.get("game_id")
    if not game_id:
        game_id = 0
        redis.set("game_id", game_id)
    redis.incr("game_id")

    game = {}
    game["id"] = game_id
    game["ip"] = request.remote_addr
    game["start"] = time.time()

    redis.hset("games",game_id, json.dumps(game))

    return str(redis.hgetall("games"))

@app.route("/game/<int:game_id>/")
def game(game_id):
    cur_game = redis.hget("games", game_id)
    if not cur_game:
        abort(404)
    else:
        return str(cur_game)

@app.route("/game/ping/<int:game_id>/")
def ping(game_id):
    logging.error("ping")
    json_game = redis.hget("games", game_id)
    if not json_game:
        abort(404)
    logging.error(json_game)
    game = json.loads(json_game)
    game["start"] = time.time()
    redis.hset("games", game_id, json.dumps(game))
    return str(game_id)


@app.route("/game/delete/<int:game_id>/")
def delete(game_id):
    delete = redis.hdel("games", game_id)
    if not delete:
        abort(404)
    else:
        return str(game_id)


@app.route("/games/")
def list():
    # delete stale games
    logging.error("list")
    delete_stale_games()
    output = []
    games = redis.hgetall("games")
    for i in games:
        output.append(json.loads(games[i]))
    return json.dumps(output)

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)
