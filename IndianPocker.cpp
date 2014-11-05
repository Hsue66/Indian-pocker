#include<iostream>
#include<cstdlib>
#include<ctime>

using namespace std;

int cardDeck[10];

int chips=20;	// 총 칩수
int betchip=0;	// 배팅칩
int cchips=20;	// 컴퓨터 칩수
int cbetchip=0;	// 컴 배팅칩

int temp=0;

int total=0;	// 배팅된 칩 수

	int card=0;		// player카드
	int card1=0;	// 컴퓨터카드

int distributeCard()	// 카드 분배함수
{
	int wrong;
	int tcard;

	do
	{
		wrong=0;
		tcard=rand()%10;	// 0~9까지

		// 나온 카드 표시하기 (2개이상 중복방지)
		for(int i=0; i<10; i++)
		{
			if(i==tcard)
			{
				if(cardDeck[i]==2)
					wrong++;
				else
					cardDeck[i]=cardDeck[i]+1;
			}
		}
	}while(wrong!=0);
	
	return tcard+1;
}
	
void betting()	// 돈배팅함수
{
	cout<<" 얼마를 배팅하시겠습니까? ";
	cin>>temp;
	betchip+=temp;
	chips=chips-temp;
	cout<<endl;
	cout<<" 당신이 "<<temp<<"칩를 배팅했습니다. 누적 "<<betchip<<"개"<<endl;

		
	cbetchip+=2;
	cout<<" 컴퓨터가 2칩을 배팅했습니다. 누적 "<<cbetchip<<"개"<<endl;
	cchips=cchips-2;
		
	total=total+temp+2;
	cout<<" 현재 "<<total<<"개의 칩이 배팅되어있습니다."<<endl<<endl;
}

void showChips()	// 상태 보여주기
{
	cout<<" 내칩수: "<<chips<<endl;
	cout<<" 컴퓨터칩수: "<<cchips<<endl;
}

int main()
{
	// 카드덱
	for(int i=0; i<10; i++)
		cardDeck[i]=0;

	srand((unsigned int)time(NULL));	// 랜덤하게

	cout<<endl;
	cout<<" ===========인디언 포커============"<<endl;
	cout<<"         게임을 시작합니다."<<endl;
	cout<<" =================================="<<endl<<endl;


	for(int i=0; i<10; i++)	 // 게임시작
	{
		cout<<" <"<<i+1<<"라운드를 시작합니다>"<<endl<<endl;
		cout<<" 1칩씩 기본배팅합니다."<<endl<<endl;
		chips=chips-1;
		cchips=cchips-1;

		// 카드분배
		card = distributeCard();
		card1 = distributeCard();

		cout<<" *컴퓨터의 카드는 "<<card1<<"입니다.*"<<endl;


		cout<<" *내 카드"<<card<<endl<<endl;

		// 한 라운드 진행
		while(1)
		{
			char choice;

			cout<<" 게임을 진행하시겠습니까?(y/n)";
			cin>>choice;
			cout<<endl;
			// 게임진행
			if(choice=='y' || choice=='Y' )
			{
				betting();
				cout<<" 내 남은 칩수는: "<<chips<<endl;
				cout<<" 현재배팅된 칩 수: "<<total<<endl;
				if(chips<0)
				{
						cout<<" 칩을 모두 소진하였으므로 컴퓨터의 승리입니다."<<endl;
					}
				if(cchips<0)
					{
						cout<<" 칩을 모두 소진하였으므로 사용자의 승리입니다."<<endl;
					}
				// 같은 수 배팅된 경우
				if(betchip==cbetchip)
				{
					cout<<endl;
					cout<<" 같은 칩수가 배팅되었으므로 배팅을 종료합니다."<<endl;
					cout<<" 내카드 :" <<card <<" 컴퓨터카드 :" <<card1<<endl;
					if(card>card1)	// 본인이 이긴경우
					{
						cout<<" 당신이 승리 하였습니다. 칩을 얻습니다."<<endl<<endl;
						chips=chips+total;

						betchip=0;
						cbetchip=0;
						total=0;
						showChips();
					}
					else if(card<card1)		// 컴퓨터가 이긴경우
					{
						cout<<" 컴퓨터가 승리 하였습니다. 컴퓨터가 칩을 얻습니다."<<endl<<endl;
						cchips=cchips+total;

						betchip=0;
						cbetchip=0;
						total=0;
						showChips();
					}
					else	// 동점인 경우
					{
						cout<<" 동점이므로 칩을 유지한채 게임을 진행합니다."<<endl;
						betchip=0;
						cbetchip=0;
						showChips();
					}
					break;
				}
			}
			// 게임포기
			else
			{
				cout<<" 게임을 포기하셨습니다. 컴퓨터의 승리입니다."<<endl;
				cchips=total+cchips;

				// 10카드인 경우 패널티
				if(card==10)
				{
					cout<<" 10카드이므로 패널티가 적용됩니다. 칩10개를 잃습니다."<<endl;
					chips=chips-10;
					cchips=cchips+10;
					if(chips<0)
					{
						cout<<" 칩을 모두 소진하였으므로 컴퓨터의 승리입니다."<<endl;
					}
				}
				showChips();
				betchip=0;
				cbetchip=0;
				total=0;
				break;
			}
		}		

		// 모두 소진한 경우
		if(chips<0 || cchips<0)
		{
			if(cchips==0)
				cout<<" 컴퓨터가 칩을 모두 소진하여 승리하셨습니다."<<endl;
			else
				cout<<" 칩을 모두 소진하였으므로 컴퓨터의 승리입니다."<<endl;
			cout<<"=================================================="<<endl;
			break;
		}
		cout<<" 다음 라운드를 진행합니다."<<endl;
		cout<<"------------------------------------------------"<<endl;
	}
	return 0;
}