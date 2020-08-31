package com.tata.jiuye.portal.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Random;

/**
 * 验证码生成器
 * 
 * @author
 */
public class ValidateCode {

	// 验证码
	private String code = null;
	// 验证码图片Buffer
	private BufferedImage buffImg = null;
	// 图片的宽度。
	private int width = 124;
	// 图片的高度。
	private int height = 44;

	/**
	 * 默认构造函数,设置默认参数
	 */
	public ValidateCode() {
		this.createCodeForNum();
	}

	/**
	 * @param width
	 *            图片宽
	 * @param height
	 *            图片高
	 */
	public ValidateCode(int width, int height) {
		this.width = width;
		this.height = height;
		this.createCodeForNum();
	}

	public void createCode() {
		int x = 0, fontHeight = 0, codeY = 0;  
	    int red = 0, green = 0, blue = 0;  
	    x = width /5;//每个字符的宽度(左右各空出一个字符)  
	    codeY = height - 4;  
	    fontHeight = height - 2;//字体的高度  
		this.buffImg = new BufferedImage(this.width, this.height,BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g = buffImg.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.width, this.height);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, this.width - 1, this.height - 1);
		g.setColor(Color.GRAY);
		Random random = new Random();
		for (int i = 0; i < 40; i++) {
			int x1 = random.nextInt(this.width);
			int y1 = random.nextInt(this.height);
			int x2 = random.nextInt(10);
			int y2 = random.nextInt(10);
			g.drawLine(x1, y1, x1 + x2, y1 + y2);
		}
		Font font = new Font("Times New Roman", Font.PLAIN, fontHeight);
		g.setFont(font);

		String[] ops = { "+", "-", "=" };// 定义运算符
		int op_num = random.nextInt(2);// 随机生成一个运算符数组中的下标，从而得到随机的一个运算符。这里是0~3之间一个随机值。因为4是等号
		int num1 = 0;int num2=0;
		//确保10以内的加减
		if(op_num == 0){
			num1 = random.nextInt(10);
			num2 = random.nextInt(10-num1);
		}else if(op_num == 1){
			num1 = random.nextInt(9)+1;
			num2 = random.nextInt(num1);
		}
		
		
		
		
		String strRand1 = String.valueOf(num1);
		int red1 = random.nextInt(255);
		int green1 = random.nextInt(255);
		int blue1 = random.nextInt(255);
		g.setColor(new Color(red1, green1, blue1)); // 画出第一个操作数
		g.drawString(strRand1, x * 0 + 6, codeY);

		
		String strRand2 = (String) ops[op_num];
		int red2 = random.nextInt(255);
		int green2 = random.nextInt(255);
		int blue2 = random.nextInt(255);
		g.setColor(new Color(red2, green2, blue2)); // 画出操作运算符
		g.drawString(strRand2, x * 1 + 6, codeY);

	
		String strRand3 = String.valueOf(num2);
		int red3 = random.nextInt(255);
		int green3 = random.nextInt(255);
		int blue3 = random.nextInt(255);
		g.setColor(new Color(red3, green3, blue3)); // 画出第二个操作数
		g.drawString(strRand3, x * 2 + 6, codeY);

		String strRand4 = (String) ops[2];
		int red4 = random.nextInt(255);
		int green4 = random.nextInt(255);
		int blue4 = random.nextInt(255);
		g.setColor(new Color(red4, green4, blue4)); // 画出等号
		g.drawString(strRand4, x * 3 + 6, codeY);

		Integer randomCode = 0; // 由运算符的不同执行不同的运算，得到验证码结果值
		switch (op_num) {
		case 0:
			randomCode = num1 + num2;
			break;
		case 1:
			randomCode = num1 - num2;
			break;
		case 2:
			randomCode = num1 * num2;
			break;
		case 3:
			randomCode = num1 / num2;
			break;
		}

		this.code = randomCode.toString();

	}
	
	
	public void createCodeForNum() {
		int x = 0, fontHeight = 0, codeY = 0;  
	    int red = 0, green = 0, blue = 0;  
	    x = width /5;//每个字符的宽度(左右各空出一个字符)  
	    codeY = height - 4;  
	    fontHeight = height - 2;//字体的高度  
		this.buffImg = new BufferedImage(this.width, this.height,BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g = buffImg.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.width, this.height);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, this.width - 1, this.height - 1);
		g.setColor(Color.GRAY);
		Random random = new Random();
		for (int i = 0; i < 40; i++) {
			int x1 = random.nextInt(this.width);
			int y1 = random.nextInt(this.height);
			int x2 = random.nextInt(10);
			int y2 = random.nextInt(10);
			g.drawLine(x1, y1, x1 + x2, y1 + y2);
		}
		Font font = new Font("Times New Roman", Font.PLAIN, fontHeight);
		g.setFont(font);

		int num1 = random.nextInt(10);
		int num2=random.nextInt(10);
		int num3=random.nextInt(10);
		int num4=random.nextInt(10);

		
		
		
		
		String strRand1 = String.valueOf(num1);
		int red1 = random.nextInt(255);
		int green1 = random.nextInt(255);
		int blue1 = random.nextInt(255);
		g.setColor(new Color(red1, green1, blue1)); // 画出第一个操作数
		g.drawString(strRand1, x * 0 + 6, codeY);

		
		String strRand2 = String.valueOf(num2);
		int red2 = random.nextInt(255);
		int green2 = random.nextInt(255);
		int blue2 = random.nextInt(255);
		g.setColor(new Color(red2, green2, blue2)); // 画出操作运算符
		g.drawString(strRand2, x * 1 + 6, codeY);

	
		String strRand3 = String.valueOf(num3);
		int red3 = random.nextInt(255);
		int green3 = random.nextInt(255);
		int blue3 = random.nextInt(255);
		g.setColor(new Color(red3, green3, blue3)); // 画出第二个操作数
		g.drawString(strRand3, x * 2 + 6, codeY);

		String strRand4 = String.valueOf(num4);
		int red4 = random.nextInt(255);
		int green4 = random.nextInt(255);
		int blue4 = random.nextInt(255);
		g.setColor(new Color(red4, green4, blue4)); // 画出等号
		g.drawString(strRand4, x * 3 + 6, codeY);

		Integer randomCode = num1*1000+num2*100+num3*10+num4; // 由运算符的不同执行不同的运算，得到验证码结果值
		

		this.code = randomCode.toString();

	}

	public void write(String path) throws IOException {
		OutputStream sos = new FileOutputStream(path);
		this.write(sos);
	}

	public void write(OutputStream sos) throws IOException {
		ImageIO.write(buffImg, "png", sos);
		sos.close();
	}

	public BufferedImage getBuffImg() {
		return buffImg;
	}

	public String getCode() {
		return code;
	}

	/**
	 * 测试函数,默认生成到d盘
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ValidateCode vCode = new ValidateCode();
		try {
			String path = "D:/test/" + new Date().getTime() + ".png";
			System.out.println(vCode.getCode() + " >" + path);
			vCode.write(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
