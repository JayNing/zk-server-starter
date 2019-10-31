package com.ning.zookeeper.starter;

import com.ning.zookeeper.starter.autoconfiguration.ZkProperties;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.List;

/**
 * @Author JAY
 * @Date 2019/8/19 19:43
 * @Description TODO
 **/
public class ZookeeperTemplate {

    private ZkProperties zkProperties;
    private CuratorFramework curatorFramework;

    public ZookeeperTemplate(ZkProperties zkProperties) {
        this.zkProperties = zkProperties;
        //创建zookeeper连接
        curatorFramework = CuratorFrameworkFactory.builder().connectString(this.zkProperties.getAddress())
                .sessionTimeoutMs(this.zkProperties.getSessionTimeOut())
                //重试策略,内建有四种重试策略,也可以自行实现RetryPolicy接口
                .retryPolicy(new ExponentialBackoffRetry(1000,3)).build();
        curatorFramework.start();
    }

    /**
     * 创建持久化节点
     * @param path
     * @param value
     */
    public void createPersistentData(String path, String value){
        createData(path,value,CreateMode.PERSISTENT);
    }

    /**
     * 创建持久化有序节点
     * @param path
     * @param value
     */
    public void createPersistentSequentialData(String path, String value){
        createData(path,value,CreateMode.PERSISTENT_SEQUENTIAL);
    }
    /**
     * 创建临时节点
     * @param path
     * @param value
     */
    public void createTempData(String path, String value){
        createData(path,value,CreateMode.EPHEMERAL);
    }

    /**
     * 创建临时有序节点
     * @param path
     * @param value
     */
    public void createTempSequentialData(String path, String value){
        createData(path,value,CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    /**
     * 获取节点数据
     * @param path
     * @return
     */
    public String getData(String path){
        try {
            byte[] bytes = curatorFramework.getData().forPath(path);
            return new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取某个节点所有子节点的路径
     * @param path
     * @return
     */
    public List<String> getChildrenPath(String path){
        try {
           return curatorFramework.getChildren().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 更新节点
     * @param path
     * @param value
     */
    public void updateData(String path, String value) {
        try {
            curatorFramework.setData().forPath(path,value.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


        /**
         *  根据path删除节点
         * @param path
         * @param isDeletingChild
         */
    public void deleteData(String path, boolean isDeletingChild) {
        try {
            if (isDeletingChild){
                //删除一个节点，并且递归删除其所有的子节点
                curatorFramework.delete().deletingChildrenIfNeeded().forPath(path);
            }else {
                curatorFramework.delete().forPath(path);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

        private void createData(String path, String value, CreateMode persistent) {
        try {
            //这个creatingParentContainersIfNeeded()接口非常有用，
            // 因为一般情况开发人员在创建一个子节点必须判断它的父节点是否存在，
            // 如果不存在直接创建会抛出NoNodeException，
            // 使用creatingParentContainersIfNeeded()之后Curator能够自动递归创建所有所需的父节点。
            curatorFramework.create().creatingParentContainersIfNeeded().withMode(persistent)
                    .forPath(path,value.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
