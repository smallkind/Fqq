package com.zx.utils;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/** *//**
 * 可设置背景图片的JPanel，提供了三种显示背景图片的方式：居中、平铺和拉伸。
 * 未设置背景图片的情况下，同JPanel。
 * 
 * @author zx
 */
public class JImagePane extends JPanel
{
    private static final long serialVersionUID = -8251916094895167058L;
    
    /** *//**
     * 居中
     */
    public static final String CENTRE = "Centre";
    
    /** *//**
     * 平铺
     */
    public static final String TILED = "Tiled";

    /** *//**
     * 拉伸
     */
    public static final String SCALED = "Scaled";

    /** *//**
     * 背景图片
     */
    private Image backgroundImage;
    
    /** *//**
     * 背景图片显示模式
     */
    private String imageDisplayMode;

    /** *//**
     * 背景图片显示模式索引（引入此属性有助于必要时扩展）
     */
    private int modeIndex;

    /** *//**
     * 构造一个没有背景图片的JImagePane
     */
    public JImagePane()
    {
        this(null, CENTRE);
    }
    
    /** *//**
     * 构造一个具有指定背景图片和指定显示模式的JImagePane
     * @param image 背景图片
     * @param modeName 背景图片显示模式
     */
    public JImagePane(Image image, String modeName)
    {
        super();
        setBackgroundImage(image);
        setImageDisplayMode(modeName);
    }
    
    /** *//**
     * 设置背景图片
     * @param image 背景图片
     */
    public void setBackgroundImage(Image image)
    {
        this.backgroundImage = image;
        this.repaint();
    }

    /** *//**
     * 获取背景图片
     * @return 背景图片
     */
    public Image getBackgroundImage()
    {
        return backgroundImage;
    }

    /** *//**
     * 设置背景图片显示模式
     * @param modeName 模式名称，取值仅限于ImagePane.TILED  ImagePane.SCALED  ImagePane.CENTRE
     */
    public void setImageDisplayMode(String modeName)
    {
        if(modeName != null)
        {
            modeName = modeName.trim();
            
            //居中
            if(modeName.equalsIgnoreCase(CENTRE))
            {
                this.imageDisplayMode = CENTRE;
                modeIndex = 0;
            }
            //平铺
            else if(modeName.equalsIgnoreCase(TILED))
            {
                this.imageDisplayMode = TILED;
                modeIndex = 1;
            }
            //拉伸
            else if(modeName.equalsIgnoreCase(SCALED))
            {
                this.imageDisplayMode = SCALED;
                modeIndex = 2;
            }
            
            this.repaint();
        }
    }

    /** *//**
     * 获取背景图片显示模式
     * @return 显示模式
     */
    public String getImageDisplayMode()
    {
        return imageDisplayMode;
    }

    /** *//**
     * 绘制组件
     * @see javax.swing.JComponent#paintComponent(Graphics)
     */
   // @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        //如果设置了背景图片则显示
        if(backgroundImage != null)
        {
            int width = this.getWidth();
            int height = this.getHeight();
            int imageWidth = backgroundImage.getWidth(this);
            int imageHeight = backgroundImage.getHeight(this);

            switch(modeIndex)
            {
                //居中
                case 0:
                {
                    int x = (width - imageWidth) / 2;
                    int y = (height - imageHeight) / 2;
                    g.drawImage(backgroundImage, x, y, this);
                    break;
                }
                //平铺
                case 1:
                {
                    for(int ix = 0; ix < width; ix += imageWidth)
                    {
                        for(int iy = 0; iy < height; iy += imageHeight)
                        {
                            g.drawImage(backgroundImage, ix, iy, this);
                        }
                    }
                    break;
                }
                //拉伸
                case 2:
                {
                    g.drawImage(backgroundImage, 0, 0, width, height, this);
                    break;
                }
            }
        }
    }
}

