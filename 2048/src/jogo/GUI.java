package jogo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GUI {
	
	Game game;
	
	int frameHeight = 394;
	int frameWidth = 328;
	int gameBoardSize = 296;
	int marginSize = 16;
	Color backgroundColor =  new Color(220,210,200);
	
	Font largeFeedbackFont = new Font("Arial", 0, 40);
	Font smallFeedbackFont = new Font("Arial", 0, 20);
	
	JLabel scoreLabel;
	JLabel highScoreLabel;
	
	Map<Integer, ImageIcon> blocos; 
	GameBoard gb;
	
	MyFrame frame;
	
	public GUI() {
		game = new Game(0);
		frame = new MyFrame();
		frame.setFocusable(true);
		frame.addKeyListener(new MyFrame());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		loadNumberBlocos();
		
		gb = new GameBoard();
		
		//North Panel
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout());
		northPanel.setPreferredSize(new Dimension(frameWidth, 82));
		JLabel gameLabel = new JLabel("2048", SwingConstants.CENTER);
		gameLabel.setFont(new Font("Arial", Font.BOLD, 20));
		northPanel.add(gameLabel);
		
		scoreLabel = new JLabel("<html>Score:<br>0</html>", SwingConstants.CENTER);
		northPanel.add(scoreLabel);
		highScoreLabel = new JLabel("<html>High Score:<br>0</html>", SwingConstants.CENTER);
		northPanel.add(highScoreLabel);
		northPanel.setBackground(backgroundColor);
		
		// Other panels
		JPanel westBuffer = new JPanel();
		westBuffer.setPreferredSize(new Dimension(marginSize, gameBoardSize));
		westBuffer.setBackground(backgroundColor);
		
		JPanel eastBuffer = new JPanel();
		eastBuffer.setPreferredSize(new Dimension(marginSize, gameBoardSize));
		eastBuffer.setBackground(backgroundColor);
		
		JPanel southBuffer = new JPanel();
		southBuffer.setPreferredSize(new Dimension(frameWidth, marginSize));
		southBuffer.setBackground(backgroundColor);
		
		// Add Panels to Frame
		frame.getContentPane().add(northPanel,BorderLayout.NORTH);
		frame.getContentPane().add(westBuffer,BorderLayout.WEST);
		frame.getContentPane().add(eastBuffer,BorderLayout.EAST);
		frame.getContentPane().add(southBuffer,BorderLayout.SOUTH);
		frame.getContentPane().add(gb, BorderLayout.CENTER);
		
		frame.getContentPane().setPreferredSize(new Dimension(frameWidth, frameHeight));
		frame.pack();
		frame.setVisible(true);
		
	}

	private void loadNumberBlocos() {
		blocos = new Hashtable<Integer, ImageIcon>();
		ClassLoader cldr = this.getClass().getClassLoader();
		URL urlVazio = cldr.getResource("png64_2048/blocoVazio64x64.png");
		URL url2 = cldr.getResource("png64_2048/bloco264x64.png");
		URL url4 = cldr.getResource("png64_2048/bloco464x64.png");
		URL url8 = cldr.getResource("png64_2048/bloco864x64.png");
		URL url16 = cldr.getResource("png64_2048/bloco1664x64.png");
		URL url32 = cldr.getResource("png64_2048/bloco3264x64.png");
		URL url64 = cldr.getResource("png64_2048/bloco6464x64.png");
		URL url128 = cldr.getResource("png64_2048/bloco12864x64.png");
		URL url256 = cldr.getResource("png64_2048/bloco25664x64.png");
		URL url512 = cldr.getResource("png64_2048/bloco51264x64.png");
		URL url1024 = cldr.getResource("png64_2048/bloco102464x64.png");
		URL url2048 = cldr.getResource("png64_2048/bloco204864x64.png");
		URL url4096 = cldr.getResource("png64_2048/bloco409664x64.png");
		URL url8192 = cldr.getResource("png64_2048/bloco819264x64.png");
		URL url16384 = cldr.getResource("png64_2048/bloco1638464x64.png");
		
		blocos.put(0, new ImageIcon(urlVazio));
		blocos.put(2, new ImageIcon(url2));
		blocos.put(4, new ImageIcon(url4));
		blocos.put(8, new ImageIcon(url8));
		blocos.put(16, new ImageIcon(url16));
		blocos.put(32, new ImageIcon(url32));
		blocos.put(64, new ImageIcon(url64));
		blocos.put(128, new ImageIcon(url128));
		blocos.put(256, new ImageIcon(url256));
		blocos.put(512, new ImageIcon(url512));
		blocos.put(1024, new ImageIcon(url1024));
		blocos.put(2048, new ImageIcon(url2048));
		blocos.put(4096, new ImageIcon(url4096));
		blocos.put(8192, new ImageIcon(url8192));
		blocos.put(16384, new ImageIcon(url16384));
	}
	
	private class GameBoard extends JPanel{
		@Override
		protected void paintComponent(Graphics graphics) {
			graphics.setColor(new Color (222,184,135));
			graphics.fillRect(0, 0, this.getWidth(), getHeight());
			
			int[][] board = game.getGameBoard();
			// O loop eh dessa forma, pois o sequenciamento de espacamento
			// precisa acompanhar a ordem do Array do gameBoard 
			for(int y = 1; y< 5; y++) {
				for(int x = 1; x< 5; x++) {
					int X2 = (8 * x) + (64 * (x - 1));
					int Y2 = (8 * y) + (64 * (y - 1));

					int thisNumber = board[y-1][x-1];
					
					if(blocos.containsKey(thisNumber)) {
						ImageIcon thisBloco = (ImageIcon)blocos.get(thisNumber);
						thisBloco.paintIcon(this, graphics, X2, Y2);						
					}
				}	
			}
		}		
	}
	
	class WinBoard extends JPanel{
		@Override
		protected void paintComponent(Graphics graphics) {
			graphics.setColor(new Color (222,184,135));
			graphics.fillRect(0, 0, this.getWidth(), getHeight());
			graphics.setFont(largeFeedbackFont);
			graphics.setColor(new Color (0, 80, 0));
			graphics.drawString("You Win!", 20, 40);
			graphics.setColor(new Color (255,255,255));
			graphics.setFont(smallFeedbackFont);
			graphics.drawString("Press ENTER to play again.", 20, 70);
		}
	}
	class LoseBoard extends JPanel {
		@Override
		protected void paintComponent(Graphics graphics) {
			graphics.setColor(new Color (222,184,135));
			graphics.fillRect(0, 0, this.getWidth(), getHeight());
			graphics.setFont(largeFeedbackFont);
			graphics.setColor(new Color (80, 0, 0));
			graphics.drawString("You Lose", 20, 40);
			graphics.setFont(smallFeedbackFont);
			graphics.setColor(new Color (255,255,255));
			graphics.drawString("Press ENTER to try again.", 20, 70);
		}
	}
	
	private class MyFrame extends JFrame implements KeyListener{
		
		@Override
		public void keyPressed(KeyEvent e) {
			
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			if (game.getGameState() == "Continue") {
				
				if(key == KeyEvent.VK_UP) {
					System.out.println("Up key Released");
					game.pushUp();
					game.checkState();
					gb.repaint();
					updateScore();
				}
				else if(key == KeyEvent.VK_DOWN) {
					System.out.println("Down key Released");
					game.pushDown();
					game.checkState();
					gb.repaint();
					updateScore();
				}
				else if(key == KeyEvent.VK_LEFT) {
					System.out.println("Left key Released");
					game.pushLeft();
					game.checkState();
					gb.repaint();
					updateScore();
				}
				else if(key == KeyEvent.VK_RIGHT) {
					System.out.println("Right key Released");
					game.pushRight();
					game.checkState();
					gb.repaint();
					updateScore();
				}
				String gs = game.getGameState();
				if (gs == "Lose") {
					frame.getContentPane().remove(gb);
					frame.getContentPane().add(new LoseBoard(),  BorderLayout.CENTER);
					frame.getContentPane().invalidate();
					frame.getContentPane().validate();
				}
				else if (gs == "Win") {
					frame.getContentPane().remove(gb);
					frame.getContentPane().add(new WinBoard(),  BorderLayout.CENTER);				
					frame.getContentPane().invalidate();
					frame.getContentPane().validate();
				}
			}
			else {
				if (key == KeyEvent.VK_ENTER) {
					updateHighScore();
					game = new Game(game.getHighScore());
					frame.getContentPane().remove(((BorderLayout)getLayout()).getLayoutComponent(BorderLayout.CENTER));
					frame.getContentPane().add(gb);
					gb.repaint();
					frame.getContentPane().invalidate();
					frame.getContentPane().validate();
				}
			}
		}
		
		// Implementar essa funcao eh um requerimento de KeyListener
		@Override
		public void keyTyped(KeyEvent e) {
			
		}
		
		
	}
	
	public void updateScore() {
		scoreLabel.setText("<html>Score:<br>" + game.getScore() + "</html>");
	}
	
	public void updateHighScore() {
		int score = game.getScore();
		int highScore = game.getHighScore();
		if(score > highScore) {
			game.setHighScore(score);
			highScoreLabel.setText("<html>High Score:<br>" + game.getHighScore() + "</html>");
		}
	}
}


