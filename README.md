```shell
# 启动chroma，会在当前目录下创建数据文件 pip install chromadb==0.5.4
chroma run
# 产看向量个数
curl -X GET http://localhost:8000/api/v1/collections/e070812c-4158-42ff-8bee-a58da5b8c631/count
# 查看集合信息
curl http://localhost:8000/api/v1/collections
# 查看日志
http://localhost:8000/docs
```