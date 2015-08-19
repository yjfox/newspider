package io.newspider.restful;

import io.newspider.crawler.NewSource;
import io.newspider.database.RedisDataUtil;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestfulController {

	@RequestMapping("/list.json")
	public List<String> getList() {
		List<String> idList = RedisDataUtil
				.getNewsListFromRedis(NewspiderDemo.redisNode);
		return idList;
	}

	@RequestMapping("/{id}.json")
	public NewSource getNews(@PathVariable("id") String id) {
		NewSource news = RedisDataUtil.getNewsContentFromRedis(id,
				NewspiderDemo.redisNode);
		return news;
	}
	
	@RequestMapping("/{error}")
	public String getError(@PathVariable("error") String id) {
		return "Sorry, the path incorrect";
	}
}
